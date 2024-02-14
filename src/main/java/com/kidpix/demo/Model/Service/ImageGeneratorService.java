package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.FinalizeStoryDTO;
import com.kidpix.demo.Model.DTO.ImageGeneratorDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ImageGeneratorService {



    // animeGAN
    @Value("${animegan.python.env.path}")
    private String animeGanPythonEnvPath;

    @Value("${animegan.script.path}")
    private String animeGanScriptPath;

    @Value("${animegan.checkpoint.dir}")
    private String animeGanCheckpointDir;

    @Value("${animegan.output.dir}")
    private String animeGanOutputDir;




    //faceSwap
    @Value("${faceswap.python.env.path}")
    private String faceSwapPythonEnvPath;

    @Value("${faceswap.script.path}")
    private String faceSwapScriptPath;

    @Value("${faceswap.output.dir}")
    private String faceSwapOutputDir;

    @Autowired
    private FinalizeStoryService storyService ;

    @Autowired
    private CategoryService categoryService ;

    @Autowired
    private BookService bookService ;

    @Autowired
    private EmailService emailService ;



    public String generateImage(ImageGeneratorDTO imageGeneratorDTO) throws IOException {



        String kidName = imageGeneratorDTO.getKidName();
        String imageInputPath = imageGeneratorDTO.getImageInputPath();
        String themeName = imageGeneratorDTO.getThemeName();

        UserEntity userEntity = this.bookService.findBookById(imageGeneratorDTO.getBookId()).getUser() ;

        //String outputDir = "/var/www/html/assets/bundles/zoo/" ;

       String outputDir = runFaceSwapScript(imageInputPath, themeName, kidName);

        if(outputDir.contains("error")){
            this.emailService.sendError(userEntity.getFirstName() , userEntity.getLastName() , userEntity.getEmail()  ,kidName) ;
            return outputDir ;
        }


        File outputDir__ = new File(outputDir);

        // List all files in the directory

        String s = "" ;
        File[] files = outputDir__.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains("Swap_0.png")){
                        String targetPath = "/var/www/html/assets/cover_books/" + file.getName();
                        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File moved successfully.");
                        s = "/assets/cover_books/"+file.getName() ;
                    }
                }
            }
        } else {
            System.out.println("The specified path does not denote a directory.");
        }
        System.out.println("[DEGUB.IMAGEGEN] outputDir : " + outputDir);



        if (imageGeneratorDTO.getWant_anime() == 1) {
            outputDir = runAnimeGanScript(outputDir, kidName);
        }
        FinalizeStoryDTO finalizeStoryDTO = new FinalizeStoryDTO() ;
        finalizeStoryDTO.setStoryList(imageGeneratorDTO.getStoryList());
        finalizeStoryDTO.setThemeName(imageGeneratorDTO.getThemeName());
        finalizeStoryDTO.setKidName(imageGeneratorDTO.getKidName());
        finalizeStoryDTO.setBookId(imageGeneratorDTO.getBookId());
        finalizeStoryDTO.setImageUrls(outputDir);
        finalizeStoryDTO.setLang(imageGeneratorDTO.getLang());

        String finalPath = storyService.createStorybook(finalizeStoryDTO) ;

        BookEntity bookEntity = this.bookService.findBookById(imageGeneratorDTO.getBookId());
        bookEntity.setBookPath(finalPath);
        bookEntity.setCoverPage(s);
        this.bookService.createBook(bookEntity);


        System.out.println("[DEGUB.IMAGEGEN] fileNames : " + outputDir);


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.emailService.sendBook(userEntity.getFirstName() +" " + userEntity.getLastName() ,userEntity.getEmail() , finalPath );


        return finalPath ;

    }


    private String runFaceSwapScript(String imageInputPath, String themeName, String kidName) {
///////////////////////////////////////////////////////////////////////////////////////
        String themePath = this.categoryService.findThemePathByCatName(themeName) ;

/////////////////////////////////////////////////////////////////////////////////////////
        String outputFilename = "outputFaceSwap_" + System.currentTimeMillis() + "_" + kidName;

        System.out.println("[DEGUB.FACESWAP] input data : " + imageInputPath);

        File outDir = new File(faceSwapOutputDir + File.separator + outputFilename );

        if (!outDir.exists()) {
            if (outDir.mkdirs()){
                System.out.println("[DEGUB.FACESWAP] created folder successfully . ");
            }
            else {
                System.out.println("[DEGUB.FACESWAP] failed to create folder . ");
            }
        }

        int exitCode = executeFaceSwapScript(imageInputPath, themeName, faceSwapOutputDir + File.separator + outputFilename);

        if (exitCode == 69) {
            return "error: Face Not Found.";
        }

        System.out.println("[DEGUB.IMAGEGEN.FACESWAP] finished executing script ?? ");
        System.out.println("Output Dir From FaceSwap : " +faceSwapOutputDir + File.separator + outputFilename );
        return faceSwapOutputDir + File.separator + outputFilename;


    }

    private int executeFaceSwapScript(String imageInputPath, String themeName, String outputDir) {

        System.out.println("[DEGUB.FACESWAP] imageInputPath : " + imageInputPath);
        System.out.println("[DEGUB.FACESWAP] themeName : " + themeName);
        System.out.println("[DEGUB.FACESWAP] outputDir : " + outputDir);

        String pythonEnvPath = Paths.get(this.faceSwapPythonEnvPath).toAbsolutePath().toString();
        String scriptPath = Paths.get(this.faceSwapScriptPath).toAbsolutePath().toString();

        System.out.println("[DEGUB.FACESWAP] Python Script : " + pythonEnvPath);
        System.out.println("[DEGUB.FACESWAP] Script GAN : " + scriptPath);

        String[] command = {
                pythonEnvPath,
                scriptPath,
                "-s", imageInputPath,
                "-b", themeName,
                "-o", outputDir
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();


        try {
            Process process = processBuilder.start();

            //to show the output of the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {

            }

            int exitCode = process.waitFor();

            System.out.println("[DEGUB.FACESWAP] Exited with code " + exitCode);

            return exitCode;

        } catch (IOException | InterruptedException e) {
            System.out.println("[DEGUB.FACESWAP] error executing script : " + e.getMessage());
        }
        return 0 ;

    }

    private String runAnimeGanScript(String dirImagesInput, String kidName) {

        String outputFilename = "outputAnime_" + System.currentTimeMillis() + "_" + kidName;

        System.out.println("input data : " + dirImagesInput);

        File outDir = new File(animeGanOutputDir + File.separator + outputFilename + File.separator);

        if (!outDir.exists()) {
            outDir.mkdirs();
            System.out.println("created folder successfully . ");
        }

        executeAnimeGANScript(dirImagesInput, animeGanOutputDir + File.separator + outputFilename + File.separator);

        System.out.println("test 2 ");


        System.out.println("OutputDir  from Anime GAN : " +animeGanOutputDir + File.separator +outputFilename );

        return animeGanOutputDir + File.separator +outputFilename;

    }




    private void executeAnimeGANScript(String testDir, String outputDir) {

        System.out.println("test data : " + testDir);
        String pythonEnvPath = Paths.get(this.animeGanPythonEnvPath).toAbsolutePath().toString();
        String scriptPath = Paths.get(this.animeGanScriptPath).toAbsolutePath().toString();
        String checkpointDir = Paths.get(this.animeGanCheckpointDir).toAbsolutePath().toString();


        System.out.println("Python Script : " + pythonEnvPath);
        System.out.println("Script GAN : " + scriptPath);
        System.out.println("checkPoint : " + checkpointDir);
        String[] command = {
                pythonEnvPath,
                scriptPath,
                "--checkpoint_dir", checkpointDir,
                "--test_dir", testDir,
                "--output_dir", outputDir
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();


            // Reading the output of the script
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code " + exitCode);
            System.out.println("TEST 1 ");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }




}
