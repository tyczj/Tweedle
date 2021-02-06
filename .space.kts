/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, see https://www.jetbrains.com/help/space/automation.html
*/

job("Build and publish") {
    container("gradle:jdk11"){
        env["USERNAME"] = Params("maven-username")
        env["PASSWORD"] = Secrets("maven-password")
        
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("publish)
        }
    }
}
