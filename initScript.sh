#!/bin/bash
mysql -h localhost -u root -p < sqlScript.sql
cd target
java -jar EnsolversInterview-1.jar

 
