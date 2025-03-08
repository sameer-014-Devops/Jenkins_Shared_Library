def call(){

    script {

        def SonarQubeAPI = env.sonarName
        def SonarHome = env.sonarHome
        def Projectname = env.appName
        def Projectkey = env.userName

        withSonarQubeEnv("${SonarQubeAPI}"){
            sh "${SonarHome}/bin/sonar-scanner -Dsonar.projectName=${Projectname} -Dsonar.projectKey=${ProjectKey} -X"
        }
    }
}
