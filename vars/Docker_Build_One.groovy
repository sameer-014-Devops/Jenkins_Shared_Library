def call(){

    script {

        def latestImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:latest"
        def newImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:" + env.newVersion

        // Log Out the DockerHub Account
        sh "docker logout"
        // Build the Docker Image for Tier One
        echo "**********Building the Docker Image $newImageOne**********"
        sh "docker build -t $newImageOne ."
        // Tag the Docker Image for Tier One
        echo "**********Tagging the Docker Image $newImageOne**********"
        sh "docker tag $newImageOne $latestImageOne"
        echo "**********Docker Images are Built and Tagged Successfully**********"
        
        def latestImageExistsOne = sh(script: "docker images -q $latestImageOne", returnStdout: true)
        if (latestImageExistsOne) {
            echo "**********Docker Images are Built and Tagged Successfully**********" 
        } else {
            error "**********Docker Images are NOT Built and Tagged**********"
        }
    }
}
