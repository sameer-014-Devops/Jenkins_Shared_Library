def call(String dockerUser, String userName, String appName, String tierName, String newversion){
    
    script{
        def latestTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:latest"
        def newversionTag = "${dockerUser}/${userName}-${appName}-${tierName}-img:${newversion}"

        // Pushing to DockerHub Repository
        echo "******** Pushing Docker Image ${newversionTag} To Repository ********"
        sh "docker push ${newversionTag}"
        echo "******** Pushing Docker Image ${newversionTag} Completed ********"

        echo "******** Pushing Docker Image ${latestTag} To Repository ********"
        sh "docker push ${latestTag}"
        echo "******** Pushing Docker Image ${latestTag} Completed ********"
    }
}
