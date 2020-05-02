# CS 432 Project 3: NoSQL
### Crime and Arrest Analysis of Los Angeles from 2010 to 2019

By: Atsuyo Shimizu and Qiqi Liang

## Overview

The crime and arrest of LA datasets were retrieved from [Kaggle.com](https://www.kaggle.com/cityofLA/los-angeles-crime-arrest-data?fbclid=IwAR0U7rqlb86AJovUE5vCpwbN1Y08vfzhMYH10rimG1q5c7fBfpj_3oTOMHQ#arrest-data-from-2010-to-present.csv), and imported to a local database using MongoDB's Community Server.

## Instructions

* Make sure to start mongodb community server before running program: (using brew) ```brew services start mongodb-community@4.2```
* Build Maven Project: ```mvn package```
* Run jar file: ```java -jar target/safety-analysis-1.0-SNAPSHOT.jar```
* Clean: ```mvn clean```
* Stop mongodb community server: ```brew services stop mongodb-community@4.2```

## Resources/References

* [MongoDB Wesbite Java Tutorials](http://mongodb.github.io/mongo-java-driver/4.0/driver/getting-started/quick-start/)
* [HashMap Sorting Algorithm on geeksforgeeks](//https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/)
* [AreaVibes Wesbite](https://www.areavibes.com/los+angeles-ca/crime/)
* [21 LAPD Area Districts Map](http://assets.lapdonline.org/assets/pdf/Citywide_09.pdf)
