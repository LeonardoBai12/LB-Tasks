# LB Tasks

LB Tasks is a task management app created as the final project for the Android Development course at PUC PR's Mobile Development postgraduate program. 

## Description

LB Tasks offers the following features:
* Create new tasks
* Schedule tasks

## Technologies

The application is built using the following technologies:

* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming.
* [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) for managing UI-related data.
* [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation) for building the UI.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
* [Firebase Realtime Database](https://firebase.google.com/docs/database?hl=pt-br) for Cloud storage.
* [Firebase Authentication](https://firebase.google.com/docs/auth?hl=pt-br) for user authentication.
* [JUnit 5](https://junit.org/junit5/docs/current/user-guide) for unit testing.
* [MockK](https://mockk.io) for mocking objects in unit tests.
* [Jetpack Compose UI Testing](https://developer.android.com/jetpack/compose/testing) for UI testing.
* [Github Actions](https://docs.github.com/pt/actions/learn-github-actions) for Continuous Integration/Continuous Deployment (CI/CD)
* [Dokka](https://github.com/Kotlin/dokka) for generating documentation.
* [JaCoCo](https://www.jacoco.org) for generating test coverage reports.

## Getting Started

1. Download the LBTasks.apk file from the [Build and Deploy APK](https://github.com/LeonardoBai12/LB-Tasks/actions/workflows/build_and_deploy_workflow.yml) action artifacts.
2. Install the APK on your Android device.
3. Use the application to schedule your tasks. =)

## Quality Assurance

To ensure high-quality code, the following tools and processes are used before merging any pull requests:

* [Ktint](https://pinterest.github.io/ktlint/) is used to enforce code style guidelines.
* All unit tests are run to ensure code functionality and quality.

This process helps maintain code consistency and quality throughout the project.

## Documentation

The documentation is automatically generated and published for every push to the main branch.\
To access the documentation, download the _Lb-Tasks-Documentation_ file from the [Documentation](https://github.com/LeonardoBai12/LB-Tasks/actions/workflows/documentation_workflow.yml) action artifacts.

## Coverage Report

An unit test coverage report is generated and published for every push to the main branch.\
To access the test coverage report, download the _Lb-Tasks-Coverage-Report_ file from the [Coverage Report](https://github.com/LeonardoBai12/LB-Tasks/actions/workflows/coverage_report_worflow.yml) action artifacts.

## Demonstration

You can watch a demo of the application's features on this [YouTube Video](https://www.youtube.com/watch?v=G3XYHBhCpFI) (PT-BR).

