def call(String dockerUser, String userName, String appName, String tierOne, String tierTwo, String newVersion, String defaultVersion) {
    script {
        def latestTag = "${dockerUser}/${userName}-${appName}-${tierOne}-img:latest"
        def newTag = "${dockerUser}/${userName}-${appName}-${tierOne}-img:${newVersion}"
        def defaultTag = "${dockerUser}/${userName}-${appName}-${tierOne}-img:${defaultVersion}"
        def latestTagTwo = "${dockerUser}/${userName}-${appName}-${tierTwo}-img:latest"
        def newTagTwo = "${dockerUser}/${userName}-${appName}-${tierTwo}-img:${newVersion}"
        def defaultTagTwo = "${dockerUser}/${userName}-${appName}-${tierTwo}-img:${defaultVersion}"

        // Checking the image present or not, if present then remove it
        def imageTags = [
            latestTag,
            newTag,
            defaultTag,
            latestTagTwo,
            newTagTwo,
            defaultTagTwo
        ]
        try {
            imageTags.each { tag ->
                sh "docker rmi -f ${tag}"
                echo "Image ${tag} removed successfully..."
            }
        } catch (Exception e) {
            echo "Image ${tag} not found..."
        }
    }
}
