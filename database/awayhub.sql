-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: awayhub
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` int NOT NULL,
  `subtotal` int NOT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,1,1,3,3000),(2,2,1,2,2000),(3,3,1,1,1000),(4,1,4,1,4500),(5,1,5,2,6600),(6,1,7,2,11000),(7,1,11,1,5800),(8,3,3,1,6000),(9,4,1,1,1000),(10,4,1,1,1000),(11,5,2,2,6000),(12,6,1,1,1000),(13,7,1,1,1000),(14,8,1,1,1000),(15,9,1,1,1000),(16,10,1,1,1000),(17,11,1,1,1000),(18,12,1,1,1000),(19,13,1,1,1000),(20,14,1,1,1000),(21,15,1,1,1000),(22,16,1,1,1000),(23,17,1,1,1000),(24,18,1,1,1000),(25,19,1,1,1000),(26,20,1,1,1000),(27,5,5,1,3300),(28,5,4,1,4500),(29,5,7,2,11000),(30,10,2,2,6000),(31,10,5,1,3300),(32,10,7,1,5500);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `precio` int NOT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Galletas',1000),(2,'Leche entera',4000),(3,'Atun',6000),(4,'Avena',4500),(5,'Sal',3300),(6,'Huevos',500),(7,'Miel',5500),(8,'Pasta',5000),(9,'Vinagre',8000),(10,'Mayonesa',6850),(11,'Mermelada',5800),(12,'Mantequilla',3500),(13,'Manzana',1500),(14,'Jamon',9000),(15,'Chorizo',2500),(16,'Cepillo',400),(17,'Papel Higienico',3300),(18,'Gel',600),(19,'Jabon',5000),(20,'Jabon en polvo',6500),(21,'Gaseosa',8000),(22,'Cerveza',6850),(23,'Fosforos',800),(24,'Pilas',3900),(25,'Talco',1500),(26,'Algodón',3500),(27,'Alcohol',2500),(28,'Salsa rosada',4500),(29,'Salsa de tomate',3300);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `cedula` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Nicolas',123456789,'jnicolaschc','12345'),(2,'Jefry',987654321,'jefryn','12345'),(3,'Diego Ruiz',1061000001,'diego.ruiz','10001'),(4,'Juan Gomez',1061000002,'juan.gomez','10002'),(5,'Javier Hurtado',1061000003,'javier.h','10003'),(6,'Carlos Muñoz',1061000004,'carlos.m','10004'),(7,'Daniel Paz',1061000005,'daniel.p','10005'),(8,'Manuel Hurtado',1061000006,'manuel.h','10006'),(9,'Samuel Diaz',1061000006,'samuel.d','10006'),(10,'Fabian Perez',1061000007,'fabian.perez','10007'),(11,'Alexander Jimenez',1061000008,'alex,j','10008'),(12,'Martin Cruz',1061000009,'martin.c','10009'),(13,'Samuel Gonzalez',1061000010,'samu.g','10010'),(14,'Carlos Samboni',1061000011,'carlos.s','10011'),(15,'Nicolas Chicaiza',1061000012,'nicolas.ch','10011'),(16,'Paola Marin',1061000012,'paola.m','10012'),(17,'Laura Jimenez',1061000014,'laura.j','10014'),(18,'Antonio Muñoz',1061000015,'antoniom','10015'),(19,'Sofia Potosi',1061000016,'sofi.p','10016'),(20,'Ximena Fernandez',1061000017,'ximena.f','10017'),(21,'Julian Lopez',1061000018,'julian.l','10018');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-22 11:32:12
