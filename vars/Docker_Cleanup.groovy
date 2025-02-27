def call(String dockerUser, String userName, String appName, String newversion, String defaultVersion){
    script {
        def latestImageExists = sh(script: """docker images -q $dockerUser/$userName-$appName-img:latest""", returnStdout: true).trim()

        if (latestImageExists) {
            echo 'Docker Image with the latest tag already exists. Removing the existing image...'
            sh """docker rmi -f $dockerUser/$userName-$appName-img:latest"""
        } else {
            echo 'Docker Image with the latest tag does not exist...'
        }

        def versionedImageExists = sh(script: """docker images -q $dockerUser/$userName-$appName-img:${params.new_ver}""", returnStdout: true).trim()

        if (versionedImageExists) {
            echo 'Docker Image with the specified version already exists. Removing the existing image...'
            sh """docker rmi -f $dockerUser/$userName-$appName-img:${params.new_ver}"""
        } else {
            echo 'Docker Image with the specified version does not exist...'
        }
    }
}
