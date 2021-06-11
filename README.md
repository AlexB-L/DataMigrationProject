# Data Migration Project
The aim of the project was to create a program that could transfer a csv file into a MySQL database.

### The requirements:

- Read data from a cvs file
- Import the data into a MySQL database
- Account for any possible errors in the cvs file
- Testing to ensure the data migration has taken place
- Show the time taken for the process to be completed
- Be a multithreaded application
- Adhere to the design patters of DTO and DAO

### The result

I successfully created a multithreaded application that can read data from a cvs file and import the data to a MySQL database. <br />
Firstly, it will create a table for the data to be inserted into. The key for the table is set to a value in the data that will be unique. 
It will detect if any rows in the cvs file have a duplicate key and remove them from the import. It will then record those rows for possible inspection later. 
Finally, it will announce that the insert has finished, and the time taken for the process to complete.
After the insert I was able to check my database using MySQL Workbench and confirm that the data had indeed been inserted correctly. <br /> <br />
The number of threads that the application creates is dynamic. The cvs file is split into smaller sections, and a new thread is created and allocated per section. 
This thread will then read and insert only the section it has been allocated insuring different threads don't read the same section of data. 
The size of the sections can be adjusted thus allowing for a dynamic creation of threads and fine-tuning of the application for maximum efficiency.

### DTO and DAO

DTO (Data Transfer Object) is an object that carries data between processes. In my case I used a DTO to transfer data between the cvs file and the database.
I transfered each row of the database into a DTO. This not only allowed me to handle the data easily but also format the data into the correct format for MySQL to read. <br />
<br />
DAO (Data Access Object) is a structural pattern that allows us to isolate the application/business layer from the persistence layer using an abstract API.
I created a DAO that housed the connection to the database, the creation of a table and the insertion of data into the table.
This allowed me to abstract these processes away from the user who does not need to see them.

### Performance Testing

We were given two csv files with which to test our application, employees.csv and EmployeeRecordsLarge.csv, the former contains 10000 rows while the latter contains 65499 rows. <br />
For my performance test I averaged the time taken for the application to inset all data over ten iterations for both files. Due to being able to adjust the size of each section the cvs files are split into I was able to find the most efficient size for both files. <br />
For the file employees.csv, I found the most efficient size of each section was 250 rows and averaging a time of ~3.65 seconds. <br />
For the file EmployeeRecordsLarge.csv, I found the most efficient size of each section was 1500 rows and averaging a time of ~20.2 seconds.

