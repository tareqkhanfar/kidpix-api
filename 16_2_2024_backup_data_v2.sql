-- MySQL dump 10.13  Distrib 8.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: kid_pix
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address_tbl`
--

DROP TABLE IF EXISTS `address_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_tbl` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `home_address` varchar(512) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `work_address` varchar(512) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FKq5h89jx7mqyrg37r4bqcmsv97` (`user_id`),
  CONSTRAINT `FKq5h89jx7mqyrg37r4bqcmsv97` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_tbl`
--

LOCK TABLES `address_tbl` WRITE;
/*!40000 ALTER TABLE `address_tbl` DISABLE KEYS */;
INSERT INTO `address_tbl` VALUES (2,'1','12','jien','21','jenin','12',2);
/*!40000 ALTER TABLE `address_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_tbl`
--

DROP TABLE IF EXISTS `book_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_tbl` (
  `book_id` bigint NOT NULL AUTO_INCREMENT,
  `age` int DEFAULT NULL,
  `book_path` varchar(2000) DEFAULT NULL,
  `cover_page` varchar(255) DEFAULT NULL,
  `created_book` datetime(6) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `kid_name` varchar(255) DEFAULT NULL,
  `kid_photo` varchar(2000) DEFAULT NULL,
  `additional_notes` varchar(2000) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `cat_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `FKs8ejkm5a803dfiumff5100esu` (`cat_id`),
  KEY `FKr9jlnl3yayredy2wvh7k3v17l` (`user_id`),
  CONSTRAINT `FKr9jlnl3yayredy2wvh7k3v17l` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`user_id`),
  CONSTRAINT `FKs8ejkm5a803dfiumff5100esu` FOREIGN KEY (`cat_id`) REFERENCES `category_tbl` (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_tbl`
--

LOCK TABLES `book_tbl` WRITE;
/*!40000 ALTER TABLE `book_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_tbl`
--

DROP TABLE IF EXISTS `category_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_tbl` (
  `cat_id` bigint NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `theme_example` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `theme_image_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `scribus_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `theme_dir_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_tbl`
--

LOCK TABLES `category_tbl` WRITE;
/*!40000 ALTER TABLE `category_tbl` DISABLE KEYS */;
INSERT INTO `category_tbl` VALUES (4,'SuperMan','this is a SuperMan Description','http://206.81.27.175/version/api.pdf','/assets/bundles/superman/Swap_0.png','/home/scribus_files/super_man.sla','/var/www/html/assets/bundles/superman'),(6,'school','this is a School Description','http://206.81.27.175/version/api.pdf','/assets/bundles/school/Swap_0.png','/home/scribus_files/school.sla','/var/www/html/assets/bundles/school'),(7,'zoo','this is a Dragon Description','http://206.81.27.175/version/api.pdf','/assets/bundles/zoo/Swap_0.png','/home/scribus_files/zoo.sla','/var/www/html/assets/bundles/zoo');
/*!40000 ALTER TABLE `category_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physical_book_tbl`
--

DROP TABLE IF EXISTS `physical_book_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `physical_book_tbl` (
  `physical_book_id` bigint NOT NULL AUTO_INCREMENT,
  `num_copies` int DEFAULT NULL,
  `request_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `book_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`physical_book_id`),
  KEY `FKg6q1w60ms2ye3r697ihbf1j0e` (`address_id`),
  KEY `FKmnfr4uxqqr11cv3qbm602h16l` (`book_id`),
  KEY `FK5ox7afpvn96m32jkifbml85bv` (`user_id`),
  CONSTRAINT `FK5ox7afpvn96m32jkifbml85bv` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`user_id`),
  CONSTRAINT `FKg6q1w60ms2ye3r697ihbf1j0e` FOREIGN KEY (`address_id`) REFERENCES `address_tbl` (`address_id`),
  CONSTRAINT `FKmnfr4uxqqr11cv3qbm602h16l` FOREIGN KEY (`book_id`) REFERENCES `book_tbl` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physical_book_tbl`
--

LOCK TABLES `physical_book_tbl` WRITE;
/*!40000 ALTER TABLE `physical_book_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `physical_book_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews_tbl`
--

DROP TABLE IF EXISTS `reviews_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews_tbl` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `rating` tinyint DEFAULT NULL,
  `rating_date` datetime(6) DEFAULT NULL,
  `review_txt` varchar(2000) DEFAULT NULL,
  `theme_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews_tbl`
--

LOCK TABLES `reviews_tbl` WRITE;
/*!40000 ALTER TABLE `reviews_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scene_tbl`
--

DROP TABLE IF EXISTS `scene_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scene_tbl` (
  `scene_id` bigint NOT NULL AUTO_INCREMENT,
  `defualt_story_text` varchar(4000) DEFAULT NULL,
  `keywords` varchar(2000) DEFAULT NULL,
  `page_number` tinyint DEFAULT NULL,
  `scene_path` varchar(2000) DEFAULT NULL,
  `cat_id` bigint DEFAULT NULL,
  `defualt_story_text_ar` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`scene_id`),
  KEY `FKl69f7is22marhkr2oli0hckn4` (`cat_id`),
  CONSTRAINT `FKl69f7is22marhkr2oli0hckn4` FOREIGN KEY (`cat_id`) REFERENCES `category_tbl` (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scene_tbl`
--

LOCK TABLES `scene_tbl` WRITE;
/*!40000 ALTER TABLE `scene_tbl` DISABLE KEYS */;
INSERT INTO `scene_tbl` VALUES (1,'[NAME]','[NAME]',1,'/assets/bundles/school/Swap_0.png',6,'[الاسم]'),(2,'Once in a small town, there lived a girl named [NAME]. She loved animals dearly. One sunny morning, while walking to school, [NAME] saw a small, scared puppy hiding under a bush.','Colorful kindergarten building#Flag on the roof#Blue sky with clouds#Red tiled roof#Yellow school bus#Striped awning#Playground slide#Vibrant flowerbeds#Stone pathway#Picket fence#Roundabout road',2,'/assets/bundles/school/Swap_1.png',6,'ذات مرة، عاشت فتاة تدعى [الاسم] في بلدة صغيرة. كانت تحب الحيوانات كثيرا. في صباح أحد الأيام المشمسة، أثناء سيرها إلى المدرسة، رأت [الاسم] جروًا صغيرًا خائفًا يختبئ تحت الأدغال.'),(3,'[NAME] gently approached the puppy. Don\'t be scared, she whispered. The puppy, wagging its tail, peeked out. You\'re lost, aren\'t you? [NAME] thought. She knew she had to help.','Schoolyard with playground#Children playing#Sports courts (basketball and soccer)#School building#Blue sky with fluffy clouds#Green trees#Lush grass#Urban background with skyscrapers#School flag#Park benches#Toy balls',3,'/assets/bundles/school/Swap_2.png',6,'[الاسم] اقتربت بلطف من الجرو. همست: لا تخافوا. وألقى الجرو نظرة خاطفة على الخارج وهو يهز ذيله. أنت ضائع، أليس كذلك؟ فكرت [الاسم]. كانت تعلم أن عليها المساعدة.'),(4,'At school, [NAME] told her friends about the puppy. Let\'s start a pet rescue mission! exclaimed her friend, Mia. They all agreed. It was time to help the lost and stray pets!','Outdoor school playground#Green trees and bushes#School building with clock#Basketball court#Swings and slides#Bench and picnic table#Fenced grass area#Colorful balls#Paved pathway',4,'/assets/bundles/school/Swap_3.png',6,'في المدرسة، أخبرت [الاسم] أصدقاءها عن الجرو. لنبدأ مهمة إنقاذ الحيوانات الأليفة! صاحت صديقتها، ميا. اتفقوا جميعا. لقد حان الوقت لمساعدة الحيوانات الأليفة الضالة والضالة!'),(5,'They made colorful posters and put them around the neighborhood. Help us find homes for lost pets! the posters read. The children felt excited to make a difference.\n','Classroom interior#Blackboard with math equations#Student character#Educational toys and blocks#Wall clock#Storage shelves with books and materials#Potted plants#Cloud and arithmetic symbols',5,'/assets/bundles/school/Swap_4.png',6,'لقد صنعوا ملصقات ملونة ووزعوها في جميع أنحاء الحي. ساعدونا في العثور على منازل للحيوانات الأليفة المفقودة! قراءة الملصقات. شعر الأطفال بالحماس لإحداث فرق.'),(6,'Soon, people started calling. We found a kitten! or There\'s a lost bunny here! The children were busy. Every pet mattered to them.\n','Fruit-themed math garden#Numbered fruit trees (apple, orange)#Addition and subtraction signs#Fruit baskets#Wooden fence with math problems#Stone pathway#Lush vegetation and flowers ',6,'/assets/bundles/school/Swap_5.png',6,'وسرعان ما بدأ الناس في الاتصال. وجدنا قطة صغيرة! أو هناك أرنب ضائع هنا! كان الأطفال مشغولين. كل حيوان أليف مهم بالنسبة لهم.'),(7,'They took care of each animal. They fed them, cleaned them, and gave them love. [NAME] felt happy seeing the animals getting healthier and happier.\n','Orchard with arithmetic operations#Flying apples with numbers#Sun and clouds#Orange and apple trees#Flowerbeds#Wooden footbridge#River',7,'/assets/bundles/school/Swap_6.png',6,'لقد اعتنوا بكل حيوان. لقد أطعموهم ونظفوهم وأعطوهم الحب. شعرت [الاسم] بالسعادة عندما رأت الحيوانات تصبح أكثر صحة وسعادة.'),(8,'The children found families who wanted pets. We promise to love and care for them, the families would say. Each goodbye was bittersweet but happy.\n','Classroom setting#Chalkboard with numbers#Desks with books and notebooks#World globes#Bookshelves with colorful bins#Hanging ceiling lights#Clock and wall decorations',8,'/assets/bundles/school/Swap_7.png',6,'وجد الأطفال عائلات تريد حيوانات أليفة. تقول العائلات: نحن نعد بأن نحبهم ونعتني بهم. كان كل وداع حلو ومر ولكنه سعيد.'),(9,'One day, the mayor visited. You kids are our town\'s heroes! he said. He gave them a special award for their kindness and hard work.\n','Bright classroom with colorful decor#Blackboard with educational drawings#Rainbow carpet#Desks with school supplies#Shelves filled with books#Educational posters#World map',9,'/assets/bundles/school/Swap_8.png',6,'في أحد الأيام، زار العمدة. أنتم أيها الأطفال أبطال مدينتنا! هو قال. وقدم لهم جائزة خاصة على لطفهم وعملهم الجاد.'),(10,'[NAME]','[NAME]',1,'/assets/bundles/superman/Swap_0.png',4,'[الاسم]'),(11,'In the bustling city\'s sky, [NAME] takes flight, spreading his wings wide. Like a free bird, he touches the clouds, wearing his bravery as a cape, searching for those in dire need.','City#Sky#Flight#Freedom#Clouds#Bravery#Cape#Search#Need#Help',2,'/assets/bundles/superman/Swap_1.png',4,'في سماء المدينة المزدحمة، يطير [الاسم]، ينشر جناحيه واسعين. كالطيور الحرة، يلامس الغيوم، مرتديًا رداء الشجاعة، يبحث عن أولئك الذين في أمس الحاجة إلى المساعدة.'),(12,'[NAME] senses hearts pulsating with worry, rushing towards the calls for help. Weaving through alleys and streets, his mere presence brings a wave of calm.','Hearts#Worry#Rush#Help#Alleys#Streets#Presence#Calm#Comfort#Safety',3,'/assets/bundles/superman/Swap_2.png',4,'يشعر [الاسم] بقلوب تنبض بالقلق، يسرع نحو النداءات المستغيثة. يشق طريقه بين الأزقة والشوارع، وبمجرد ظهوره، يُشعر الجميع بالأمان.'),(13,'Flames surround the buildings, cries rise high. [NAME] arrives swiftly, his eyes scanning the scene, preparing for the daunting task ahead.','Flames#Buildings#Cries#Arrival#Swift#Scan#Prepare#Task#Daunting#Scene',4,'/assets/bundles/superman/Swap_3.png',4,'النيران تحاصر المباني والصرخات تعلو. [الاسم] يصل مسرعًا، وعيونه تتفحص المكان، يعد نفسه للمهمة الشاقة.'),(14,'He plunges into the epicenter, where people are trapped, fear evident in their eyes. With courage and resolve, [NAME] begins freeing them one by one.','Epicenter#Trapped#Fear#Eyes#Courage#Resolve#Free#One by one#Help#Rescue',5,'/assets/bundles/superman/Swap_4.png',4,'يندفع إلى قلب الحدث، حيث الناس محاصرون والخوف في عيونهم. بشجاعة وتصميم، يبدأ [الاسم] في تحريرهم واحدًا تلو الآخر.'),(15,'Moving with agility and speed, he rescues the trapped, clearing debris. His bravery lights up the place like a lamp guiding those in darkness.','Agility#Speed#Rescue#Trapped#Debris#Bravery#Light#Guidance#Darkness#Hope',6,'/assets/bundles/superman/Swap_5.png',4,'يتحرك بخفة وسرعة، ينقذ المحاصرين، يبعد عنهم الركام. شجاعته تضيء المكان كمصباح يهدي السائرين في الظلام.'),(16,'After completing his mission, [NAME] disappears like a shadow in the light. He waits not for thanks, as his noble deeds leave a deeper imprint than words.','Mission#Complete#Disappear#Shadow#Light#Thanks#Deeds#Imprint#Noble#Silent',7,'/assets/bundles/superman/Swap_6.png',4,'بعد انتهاء مهمته، يختفي [الاسم] كظل في الضوء. لا ينتظر الشكر، فأفعاله النبيلة تترك بصمة أعمق من الكلمات.'),(17,'The police follow the trail of heroism, but unknown to them, the hero is [NAME]. In the shadows, he watches and smiles, having cleverly evaded their net.','Police#Trail#Heroism#Unknown#Hero#Ahmad#Shadows#Watch#Smile#Evasion',8,'/assets/bundles/superman/Swap_7.png',4,'الشرطة تتبع أثر البطولة، لكنهم لا يعلمون أن البطل هو [الاسم]. في الظل، يراقب ويبتسم، فقد تمكن من التملص بذكاء من شباكهم.'),(26,'Today is a sunny Friday. [NAME] is excited. He decides to visit the forest nearby. He imagines tall trees and animals. He puts on his shoes, takes a small bag, and starts walking. He can\'t wait to see what he\'ll find. The forest looks green and big. He steps in, ready for adventure.','Sunny#Friday#Excited#Forest#Adventure#Trees#Animals#Green#Walking',2,'/assets/bundles/zoo/Swap_1.png',7,'اليوم يوم جمعة مشمس. [الاسم] متحمس. يقرر زيارة الغابة القريبة. يتخيل الأشجار الطويلة والحيوانات. يرتدي حذائه ويأخذ حقيبة صغيرة ويبدأ بالمشي. لا يمكنه الانتظار ليرى ما سيجده. تبدو الغابة خضراء وكبيرة. يتدخل وهو جاهز للمغامرة.'),(27,'First, he sees deer. They are eating grass in a clearing. Their eyes are gentle. [NAME] watches quietly. The deer seem calm and free. He feels happy just looking at them. They don\'t notice him. He takes out his notebook and writes, \"Deer are peaceful.\" He smiles and walks on.','Deer#Grass#Clearing#Eyes#Gentle#Watches#Calm#Free#Notebook#Peaceful#Smiles ',3,'/assets/bundles/zoo/Swap_2.png',7,'أولا يرى الغزلان. إنهم يأكلون العشب في المقاصة. عيونهم لطيفة. [الاسم] يشاهد بهدوء. يبدو الغزلان هادئًا وحرًا. إنه يشعر بالسعادة بمجرد النظر إليهم. لا يلاحظونه. أخرج دفتر ملاحظاته وكتب: \"الغزلان مسالمة\". يبتسم ويمضي.'),(28,'[NAME] finds a place with many butterflies. They fly around him. Their wings have many colors like a rainbow. They land softly on flowers. Some fly close to him. He tries to count them but there are too many. It\'s like a dream. He feels like he\'s in a fairy tale.','Butterflies#Fly#Wings#Colors#Rainbow#Flowers#Dream#FairyTale#Count',4,'/assets/bundles/zoo/Swap_3.png',7,'[الاسم] يجد مكانًا به العديد من الفراشات. يطيرون حوله. أجنحتها لها ألوان كثيرة مثل قوس قزح. يهبطون بهدوء على الزهور. والبعض يطير بالقرب منه. يحاول عدهم ولكن هناك الكثير منهم. انها مثل حلم. يشعر وكأنه في قصة خيالية.'),(29,'Next, he hears laughter. It\'s monkeys! They swing from tree to tree. They play tag and chase each other. They seem to be having fun. [NAME] laughs too. The monkeys are funny and full of energy. He wishes he could climb trees like them. He watches them for a long time.','Laughter#Monkeys#Swing#Trees#Play#Tag#Chase#Fun#Energy#Climb',5,'/assets/bundles/zoo/Swap_4.png',7,'وبعد ذلك يسمع الضحك. إنها القرود! يتأرجحون من شجرة إلى أخرى. يلعبون العلامة ويطاردون بعضهم البعض. يبدو أنهم يستمتعون. [الاسم] يضحك أيضًا. القرود مضحكة ومليئة بالطاقة. يتمنى أن يتمكن من تسلق الأشجار مثلهم. يراقبهم لفترة طويلة.'),(30,'Then, [NAME] spots a fox. It walks with soft steps. He reads in his book, \"Foxes are clever.\" The fox has a red coat and sharp ears. It looks around and then disappears into the bushes. [NAME] feels excited to see a fox. He never saw one before today.','Fox#SoftSteps#Book#Clever#RedCoat#SharpEars#Bushes#Excited#Disappears#FirstSighting',6,'/assets/bundles/zoo/Swap_5.png',7,'ثم اكتشف [الاسم] ثعلبًا. يمشي بخطوات ناعمة. يقرأ في كتابه \"الثعالب ذكية\". الثعلب لديه معطف أحمر وآذان حادة. ينظر حوله ثم يختفي في الأدغال. [الاسم] يشعر بالإثارة لرؤية ثعلب. ولم ير واحدة قبل اليوم.'),(31,'It\'s time for lunch. [NAME] sits under a big tree. He eats his sandwiches. He shares a piece with a squirrel. The squirrel takes it and runs up the tree. [NAME] laughs. He watches the squirrels play. They jump from branch to branch. Lunchtime with squirrels is fun.','Lunch#BigTree#Sandwiche#Squirrel#Runs#Watches#Squirrel steal Sandwiche',7,'/assets/bundles/zoo/Swap_6.png',7,'إنه وقت الغداء. [الاسم] يجلس تحت شجرة كبيرة. يأكل شطائره. يتقاسم قطعة مع السنجاب. يأخذها السنجاب ويركض نحو الشجرة. [الاسم] يضحك. يشاهد السناجب تلعب. يقفزون من فرع إلى فرع. وقت الغداء مع السناجب ممتع.'),(32,'[NAME] walks to a small creek. He sees frogs jumping. Snakes are swimming in the water. They move without a sound. He sits and watches. The frogs say, \"Ribbit.\" The water is clear. He can see small fish too. The creek is full of life. It\'s a new world for him.','Creek#Frogs#Jumping#Snakes#Swimming#Watches#Ribbit#ClearWater#Life#NewWorld',8,'/assets/bundles/zoo/Swap_7.png',7,'[الاسم] يسير إلى جدول صغير. يرى الضفادع تقفز. الثعابين تسبح في الماء. يتحركون بدون صوت. يجلس ويشاهد. الضفادع تقول \"ريبت\". الماء واضح. يمكنه رؤية الأسماك الصغيرة أيضًا. الخور مليء بالحياة. إنه عالم جديد بالنسبة له.'),(33,'He finds a place with many birds. They are singing. There are big birds and small birds. Birds with long tails and birds with bright feathers. They sing different songs. It\'s like music. [NAME] closes his eyes and listens. The bird songs make him feel calm and happy.','Birds#Singing#Big#Small#LongTails#BrightFeathers#DifferentSongs#music#Calm#Happy',9,'/assets/bundles/zoo/Swap_8.png',7,'يجد مكانًا به العديد من الطيور. انهم يغنون. هناك الطيور الكبيرة والطيور الصغيرة. الطيور ذات الذيول الطويلة والطيور ذات الريش اللامع. يغنون أغاني مختلفة. إنها مثل الموسيقى. [الاسم] يغلق عينيه ويستمع. أغاني الطيور تجعله يشعر بالهدوء والسعادة.'),(34,'[NAME] sits on a rock. He thinks about the forest. He understands it\'s a special place. We must take care of it. He feels lucky to be there. The trees, the animals, they all have a home here. He wants to help keep the forest safe for them.','Rock#Forest#Special#Care#Lucky#Trees#Animals#Home#Safe',10,'/assets/bundles/zoo/Swap_9.png',7,'[الاسم] يجلس على صخرة. يفكر في الغابة. إنه يفهم أنه مكان خاص. يجب أن نعتني به. إنه يشعر بأنه محظوظ لوجوده هناك. الأشجار، الحيوانات، جميعهم لديهم منزل هنا. يريد المساعدة في الحفاظ على الغابة آمنة لهم.'),(35,'[NAME]','[NAME]',1,'/assets/bundles/zoo/Swap_0.png',7,'[الاسم]'),(36,'As the city awakens to a new day, [NAME] remains ready. He stands tall as a faithful guardian, not a mythical hero but one among us, igniting hope in hearts and awakening dreams.','Awaken#New day#Ready#Stand#Guardian#Faithful#Mythical#Real#Hope#Dreams',10,'/assets/bundles/superman/Swap_9.png',4,'ومع استيقاظ المدينة ليوم جديد، يظل [الاسم] مستعدًا. يقف شامخًا كحارس أمين، ليس بطلاً من الأساطير بل بطلاً من بيننا. يُشعل الأمل في القلوب ويوقظ الأحلام.'),(37,'backpage','Walking#Home#Happy#Deer#Monkeys#Fox#Birds#Family#Forest#Adventures',11,'/assets/bundles/superman/Swap_A.png',4,'[الاسم]'),(38,'The next morning, [NAME] walks through the quiet streets, breathing in the pure dawn air, each step promising safety and tranquility.','Morning#Quiet#Streets#Dawn#Air#Pure#Safety#Tranquility#Promise#Walk',9,'/assets/bundles/superman/Swap_8.png',4,'في الصباح التالي، يمشي [الاسم] بين الشوارع الهادئة، يتنسم هواء الفجر النقي، وكل خطوة له تعد بالأمان والسكينة.'),(39,'Walking back home, [NAME] feels happy. He remembers the deer, the monkeys, the fox, and the birds. He can\'t wait to tell his family about his day. He knows he will come back to the forest again. It\'s a place full of wonders and adventures.','Walking#Home#Happy#Deer#Monkeys#Fox#Birds#Family#Forest#Adventures',11,'/assets/bundles/zoo/Swap_A.png',7,'عند العودة إلى المنزل، يشعر [الاسم] بالسعادة. يتذكر الغزلان والقرود والثعلب والطيور. لا يستطيع الانتظار ليخبر عائلته عن يومه. إنه يعلم أنه سيعود إلى الغابة مرة أخرى. إنه مكان مليء بالعجائب والمغامرات.'),(40,'BackPage','[NAME]',12,'/assets/bundles/zoo/Swap_B.png',7,'[الاسم]'),(41,'Looking at all the happy pets with their new families, [NAME] felt proud. We did this together, she smiled. The Pet Rescue Mission was a success!\n','Bright classroom with colorful decor#Blackboard with educational drawings#Rainbow carpet#Desks with school supplies#Shelves filled with books#Educational posters#World map',10,'/assets/bundles/school/Swap_9.png',6,'عند النظر إلى جميع الحيوانات الأليفة السعيدة مع عائلاتهم الجديدة، شعرت [الاسم] بالفخر. لقد فعلنا هذا معًا، ابتسمت. كانت مهمة إنقاذ الحيوانات الأليفة ناجحة!'),(42,'From that day, [NAME] and her friends continued to help animals. Everyone knew them as the kind kids who loved pets. And the pets loved them back, forever.\n','Bright classroom with colorful decor#Blackboard with educational drawings#Rainbow carpet#Desks with school supplies#Shelves filled with books#Educational posters#World map',11,'/assets/bundles/school/Swap_A.png',6,'ومنذ ذلك اليوم، واصلت [الاسم] وأصدقاؤها مساعدة الحيوانات. عرفهم الجميع بأنهم الأطفال الطيبون الذين يحبون الحيوانات الأليفة. وقد أحبتهم الحيوانات الأليفة إلى الأبد.'),(43,'backpage','Bright classroom with colorful decor#Blackboard with educational drawings#Rainbow carpet#Desks with school supplies#Shelves filled with books#Educational posters#World map',12,'/assets/bundles/school/Swap_B.png',6,'[الاسم]');
/*!40000 ALTER TABLE `scene_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_tbl`
--

DROP TABLE IF EXISTS `story_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_tbl` (
  `story_id` bigint NOT NULL AUTO_INCREMENT,
  `story_text` varchar(4000) DEFAULT NULL,
  `book_id` bigint DEFAULT NULL,
  PRIMARY KEY (`story_id`),
  KEY `FK5qqf91s4rl17xdarljjgaqnxa` (`book_id`),
  CONSTRAINT `FK5qqf91s4rl17xdarljjgaqnxa` FOREIGN KEY (`book_id`) REFERENCES `book_tbl` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_tbl`
--

LOCK TABLES `story_tbl` WRITE;
/*!40000 ALTER TABLE `story_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_admin` tinyint DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `security_code` int DEFAULT NULL,
  `status_account` tinyint DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` VALUES (2,'tareqkh2016@gmail.com','tareq',1,'khanfar','$2a$10$2etwjXMisphRG4ileQSu5epFCBwEB28XWb9akwdyS407iJsIXWdFm',NULL,1,'tareq_khanfar');
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16  8:01:19
