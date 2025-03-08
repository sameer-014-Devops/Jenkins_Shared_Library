def call(){

    script {

        def latestImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:latest"
        def newImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:" + env.newVersion

        echo "**********Building the Docker Image $newImageTwo**********"
        sh "docker build -t $newImageTwo ."
        // Tag the Docker Image for Tier Two
        echo "**********Tagging the Docker Image $newImageTwo**********"
        sh "docker tag $newImageTwo $latestImageTwo"
        echo "**********Docker Images are Built and Tagged Successfully**********"
        
        def latestImageExistsTwo = sh(script: "docker images -q $latestImageTwo", returnStdout: true)
        if (latestImageExistsTwo) {
            echo "**********Docker Images are Built and Tagged Successfully**********" 
        } else {
            error "**********Docker Images are NOT Built and Tagged**********"
        }
    }
}
