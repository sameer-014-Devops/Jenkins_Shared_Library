def call(){

    script {

        def SonarQubeAPI = env.sonarName
        def Projectname = env.appName
        def Projectkey = env.userName

        withSonarQubeEnv("${SonarQubeAPI}"){
          sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=${Projectname} -Dsonar.projectKey=${ProjectKey} -X"
      }
    }
}
