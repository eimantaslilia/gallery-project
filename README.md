# Gallery - a web app for sharing images


This is a web application made to showcase CRUD operations using REST on images.  


The users can collaborate on creating a gallery of pictures and share them with the world. 


<strong>Note:</strong> The project is no longer hosted in favor of hosting the E-Commerce Bookstore project using the AWS Free Tier.

![alt text](https://i.imgur.com/PFWq0oD.jpg)
## About the Project



* The project is hosted on AWS Elastic Beanstalk.
* The images are stored in a bucket using Amazon S3.
* The information about the images is stored in a MySQL database using Amazon RDS, such as names, paths to S3 and ids.
* Thymeleaf security extras used in conjunction with Spring Security allows us to hide certain functionalities from users who are not logged in, such as delete and upload buttons.
* There are accounts that have been set-up using Spring Security with which the users can test the full functionality which includes uploading and deleting images.
* Every time someone uploads an image, the owner receives an email notification that an image has been uploaded by the user that is currently logged in.
* Pagination has been configured, and the most recently uploaded images will show up as first in pages of 18 images each.


## Technologies used
* Spring Boot
* Hibernate
* MySQL
* Maven
* Thymeleaf
* HTML
* CSS
* Bootstrap
* AWS


## Challenges
 
This is my first personal project and it went through many iterations.  
In the beginning, I was storing the images locally in a folder, and using a MySQL database. Once I was almost done with the functionality of the project and the frontend, I figured that I need to find a way to store the images and information remotely.
That led me to learning about AWS and its services such as RDS, S3, IAM and Elastic Beanstalk.  
  
Throughout this project I solidified my knowledge about Spring Boot and other Spring Framework's projects, REST, Maven, Hibernate and MySQL. Learned more about the front-end, especially Thymeleaf and CSS, and gained more knowledge about developing an application from beginning to end.


## Contact
If you have any questions, feel free to contact me on my [website](https://eimantaslilia.herokuapp.com/).
