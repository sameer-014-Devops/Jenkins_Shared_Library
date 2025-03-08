def call(){

    script {

        def latestImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:latest"
        def defaultImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:" + env.defaultVersion
        def newImageOne = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierOne + "-img:" + env.newVersion
        def latestImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:latest"
        def defaultImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:" + env.defaultVersion
        def newImageTwo = env.dockerhubUser + "/" + env.userName + "-" + env.appName + "-" + env.tierTwo + "-img:" + env.newVersion

        def latestImageExistsOne = sh(script: "docker images -q $latestImageOne", returnStdout: true)
        if (latestImageExistsOne) {
            echo "********** Docker Image $latestImageOne already exists... Removing the existing Image **********"
            sh "docker rmi -f $latestImageOne"
        } else {
            echo "********** Docker Image $latestImageOne does not exist **********"
        }

        def latestImageExistsTwo = sh(script: "docker images -q $latestImageTwo", returnStdout: true)
        if (latestImageExistsTwo) {
            echo "********** Docker Image $latestImageTwo already exists... Removing the existing Image **********"
            sh "docker rmi -f $latestImageTwo"
        } else {
            echo "********** Docker Image $latestImageTwo does not exist **********"
        }

        def defaultImageExistsOne = sh(script: "docker images -q $defaultImageOne", returnStatus: true)
        if (defaultImageExistsOne) {
            echo "**********Docker Image $defaultImageOne already exists... Removing the existing Image**********"
            sh "docker rmi -f $defaultImageOne"
        } else {
            echo "**********Docker Image $defaultImageOne does not exist**********"
        }

        def defaultVersionExistsTwo = sh(script: "docker images -q $defaultImageTwo", returnStatus: true)
        if (defaultVersionExistsTwo) {
            echo "**********Docker Image $defaultImageTwo already exists... Removing the existing Image**********"
            sh "docker rmi -f $defaultImageTwo"
        } else {
            echo "**********Docker Image $defaultImageTwo does not exist**********"
        }

        def newVersionExistsOne = sh(script: "docker images -q $newImageOne", returnStatus: true)
        if (newVersionExistsOne) {
            echo "**********Docker Image $newImageOne already exists... Removing the existing Image**********"
            sh "docker rmi -f $newImageOne"
        } else {
            echo "**********Docker Image $newImageOne does not exist**********"
        }

        def newVersionExistsTwo = sh(script: "docker images -q $newImageTwo", returnStatus: true)
        if (newVersionExistsTwo) {
            echo "**********Docker Image $newImageTwo already exists... Removing the existing Image**********"
            sh "docker rmi -f $newImageTwo"
        } else {
            echo "**********Docker Image $newImageTwo does not exist**********"
        }
    }
}
