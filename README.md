# Spring Batch Admin EOL

[Spring Batch Admin](https://github.com/spring-attic/spring-batch-admin) has reached their end of life and not recommended for new projects. [Spring Cloud Data Flow](https://spring.io/projects/spring-cloud-dataflow) is the recommended replacement for managing and monitoring [Spring Batch](https://spring.io/projects/spring-batch/) jobs going forward. You can read more about migrating to Spring Cloud Data Flow [here](https://github.com/spring-attic/spring-batch-admin/blob/master/MIGRATION.md).

# Security Update

Pivotal [Spring Batch Admin](https://mvnrepository.com/artifact/org.springframework.batch/spring-batch-admin-manager), all versions, contains a stored [XSS vulnerability](https://cwe.mitre.org/data/definitions/79.html) in the file upload feature. An unauthenticated malicious user with network access to Spring Batch Admin could store an arbitrary web script that would be executed by other users. This issue has not been patched because Spring Batch Admin has reached end of life.

# Spring Batch Admin with Quartz implementation

This project incorporates the [Quartz](https://quartz-scheduler.org/) scheduler along with the Spring Batch admin. A new page "Quartz" is setup for accepting the CRON expression for the scheduling of the application.

# Building the project

The project can be built as a Docker container, exposed on port `9001`

```
docker-compose up --build
```

# Accessing the application

- Navigate to `http://localhost:9001/spring-batch-quartz-admin/`
- Browse to the `Quartz` tab. You can schedule the sample job and look at the executions as well

![Sample Job](sample-job.png)

![Schedule Job](schedule.png)

![Executions](execution.png)

