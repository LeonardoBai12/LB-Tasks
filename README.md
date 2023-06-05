# LB Tasks

LB Tasks is an Android application to save tasks that you have to do.

## Description

LB Tasks offers the following features:
* Create new tasks
* Schedule tasks

## Technologies

The application is built using the following technologies:

* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming
* [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) for managing UI-related data
* [Room DB](https://developer.android.com/training/data-storage) for local data storage
* [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation) for building the UI
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
* [JUnit 5](https://junit.org/junit5/docs/current/user-guide) for unit testing (Not yet implemented)
* [MockK](https://mockk.io) for mocking objects in unit tests (Not yet implemented)
* [Espresso](https://developer.android.com/training/testing/espresso) for UI testing (Not yet implemented)
* [Github Actions](https://docs.github.com/pt/actions/learn-github-actions) for Continuous Integration/Continuous Deployment (CI/CD)
* [JaCoCo](https://www.jacoco.org) for generating test coverage reports

## Getting Started

1. Download the LBTasks.apk file from the [Build and Deploy APK](https://github.com/LeonardoBai12/LB-Tasks/actions/workflows/build_and_deploy_workflow.yml) action artifacts.
2. Install the APK on your Android device.
3. Use the application to schedule your tasks. =)

## Quality Assurance

To ensure high-quality code, the following tools and processes are used before merging any pull requests:

* [Ktint](https://pinterest.github.io/ktlint/) is used to enforce code style guidelines.
* All unit tests are run to ensure code functionality and quality.

This process helps maintain code consistency and quality throughout the project.
