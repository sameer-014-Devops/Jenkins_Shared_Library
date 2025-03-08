def call(){

    script {

        def latestImageOne = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest"
        def defaultImageOne = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.defaultVersion}"
        def newImageOne = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion}"
        def latestImageTwo = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:latest"
        def defaultImageTwo = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.defaultVersion}"
        def newImageTwo = "${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion}"


        def latestImageExistsOne = sh(script: "docker images -q ${latestImageOne}", returnStatus: true)
        if (latestImageExistsOne) {
            echo "Docker Image ${env.appName}-${env.tierOne}-img:latest already exists. Removing the existing image..."
            sh "docker rmi -f ${latestImageOne}"
        } else {
            echo "Docker ${env.appName}-${env.tierOne} Image with the latest tag does not exist."
        }

        def latestImageExistsTwo = sh(script: "docker images -q ${latestImageTwo}", returnStatus: true)
        if (latestImageExistsTwo) {
            echo "Docker Image ${env.appName}-${env.tierTwo}-img:latest already exists. Removing the existing image..."
            sh "docker rmi -f ${latestImageTwo}"
        } else {
            echo "Docker ${env.appName}-${env.tierTwo} Image with the latest tag does not exist."
        }

        def defaultImageExistsOne = sh(script: "docker images -q ${defaultImageOne}", returnStatus: true)
        if (defaultImageExistsOne) {
            echo "Docker Image ${env.appName}-${env.tierOne}-img:${env.defaultVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${defaultImageOne}"
        } else {
            echo "Docker ${env.appName}-${env.tierOne} Image with the default version does not exist."
        }

        def defaultVersionExistsTwo = sh(script: "docker images -q ${defaultImageTwo}", returnStatus: true)
        if (defaultVersionExistsTwo) {
            echo "Docker Image ${env.appName}-${env.tierTwo}-img:${env.defaultVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${defaultImageTwo}"
        } else {
            echo "Docker ${env.appName}-${env.tierTwo} Image with the default version does not exist."
        }

        def newVersionExistsOne = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion}", returnStatus: true)
        if (newVersionExistsOne == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${newImageOne}"
        } else {
            echo "Docker ${env.appName}-${env.tierOne} Image with the new version does not exist."
        }

        def newVersionExistsTwo = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion}", returnStatus: true)
        if (newVersionExistsTwo == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${newImageTwo}"
        } else {
            echo "Docker ${env.appName}-${env.tierTwo} Image with the new version does not exist."
        }

    }
}
