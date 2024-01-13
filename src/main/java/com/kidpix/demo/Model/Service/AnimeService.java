package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.RequestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


@Service
public class AnimeService {

    @Value("${animegan.python.env.path}")
    private String pythonEnvPath;

    @Value("${animegan.script.path}")
    private String scriptPath;

    @Value("${animegan.checkpoint.dir}")
    private String checkpointDir;


    @Value("${animegan.output.dir}")
    private String outputDir;


    @Value("${animegan.seperator}")
    private String seperator_slash;




    public List<String> convertToAnime(RequestApi requestApi) throws IOException {

       String  outputFilename = "outputAnime_" + System.currentTimeMillis() + "_" + requestApi.getKidName();

System.out.println("input data : " + requestApi.getDirImagesInput());

File outDir = new File(outputDir+outputFilename+seperator_slash) ;

if (!outDir.exists()){
    outDir.mkdirs();
    System.out.println("created folder successfully . ");
}


        executeAnimeGANScript(requestApi.getDirImagesInput(), outputDir+ outputFilename);

        System.out.println("test 2 ");

        List<String> fileNames = new LinkedList<>();

        File outDirectory = new File(outputDir+outputFilename+seperator_slash);
        File[] files = outDirectory.listFiles();

        System.out.println("size of result : " + files.length);
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getAbsoluteFile());
                System.out.println(file.getCanonicalFile());
                fileNames.add(outputFilename+seperator_slash+file.getName());
            }

            }


        return fileNames;
    }




    public void executeAnimeGANScript(String testDir, String outputDir) {

        System.out.println("test data : " + testDir);
        String pythonEnvPath = Paths.get(this.pythonEnvPath).toAbsolutePath().toString();
        String scriptPath = Paths.get(this.scriptPath).toAbsolutePath().toString();
        String checkpointDir = Paths.get(this.checkpointDir).toAbsolutePath().toString();



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
