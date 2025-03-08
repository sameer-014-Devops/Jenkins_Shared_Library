def call(){
    
    script {
        
        def latestImageExistsOne = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest", returnStatus: true)
        if (latestImageExistsOne == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:latest"
        } else {
            echo "Docker ${env.tierOne} Image with the latest tag does not exist."
        }

        def latestImageExistsTwo = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:latest", returnStatus: true)
        if (latestImageExistsTwo == 0) {
            echo "Docker Image $latestImageExistsTwo with the latest tag already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:latest"
        } else {
            echo "Docker ${env.tierTwo} Image with the latest tag does not exist."
        }

        def defaultVersionExistsOne = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.defaultVersion}", returnStatus: true)
        if (defaultVersionExistsOne == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.defaultVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.defaultVersion}"
        } else {
            echo "Docker ${env.tierOne} Image with the default version does not exist."
        }

        def defaultVersionExistsTwo = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.defaultVersion}", returnStatus: true)
        if (defaultVersionExistsTwo == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.defaultVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.defaultVersion}"
        } else {
            echo "Docker ${env.tierTwo} Image with the default version does not exist."
        }

        def newVersionExistsOne = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion}", returnStatus: true)
        if (newVersionExistsOne == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierOne}-img:${env.newVersion}"
        } else {
            echo "Docker ${env.tierOne} Image with the new version does not exist."
        }

        def newVersionExistsTwo = sh(script: "docker images -q ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion}", returnStatus: true)
        if (newVersionExistsTwo == 0) {
            echo "Docker Image ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion} already exists. Removing the existing image..."
            sh "docker rmi -f ${env.dockerhubUser}/${env.userName}-${env.appName}-${env.tierTwo}-img:${env.newVersion}"
        } else {
            echo "Docker ${env.tierTwo} Image with the new version does not exist."
        }

    }
}
