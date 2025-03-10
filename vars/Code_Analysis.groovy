def call(){

    script {

        def Projectname = env.appName
        def ProjectKey = env.userName
        
        sh "sonar-scanner -Dsonar.projectName=$Projectname -Dsonar.projectKey=$ProjectKey -X"
    }
}
