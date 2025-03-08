def call(){

    script {

        def latestImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:latest"
        def newImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:" + env.newVersion
        def latestImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:latest"
        def newImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:" + env.newVersion

        // Pushing the new images to DockerHub
        echo "**********Pushing Docker Images $newImageOne DockerHub**********"
        sh "docker push $newImageOne"
        echo "**********Pushing Docker Images $newImageTwo DockerHub**********"
        sh "docker push $newImageTwo"

        // Pushing the latest images to DockerHub
        echo "**********Pushing Docker Images $latestImageOne DockerHub**********"
        sh "docker push $latestImageOne"
        echo "**********Pushing Docker Images $latestImageTwo DockerHub**********"
        sh "docker push $latestImageTwo"
        
        echo "**********Docker Images Pushed to DockerHub Successfully**********"
    }
}
