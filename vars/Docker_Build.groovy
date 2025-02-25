def call(String dockerUser, String userName, String appName, String tierName, String newversion){
    
    script{

        def latestTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:latest"
        def newversionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${newversion}"

        // LogOuting dockerhub account
        sh 'docker logout'

        // Building Docker image
        sh "docker build -t ${newversionTag} ."
        
        // Tag as latest
        sh "docker tag ${newversionTag} ${latestTag}"

        //verify the build
        def latestImageExists = sh(script:"docker images -q ${latestTag}", returnStdout: true).trim()
        def newversionImageExists = sh(script:"docker images -q ${newversionTag}", returnStdout: true).trim()
        if ((latestImageExists) && (newversionImageExists)){
            echo "*************** Docker Image Build Completed ***************"
        } else if ((!latestImageExists) && (newversionImageExists)){
            echo "*************** Docker Image Build Completed But Not Tagged with latest"
        } else {
            echo "*************** Docker Image Build Failed ***************"
        }
        
    }
}
