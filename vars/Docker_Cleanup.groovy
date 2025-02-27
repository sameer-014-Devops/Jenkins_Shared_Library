def call (String dockerUser, String userName, String appName, String tierOne, String tierTwo, String newVersion, String defaultVersion){

    script{

        def latestImage1Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierOne}-img:latest", returnStdout: true).trim()

        def versionImage1Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierOne}-img:${newVersion}", returnStdout: true).trim()

        def defaultImage1Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierOne}-img:${defaultVersion}", returnStdout: true).trim()

        def image1Tags = [ latestImage1Exists, versionImage1Exists, defaultImage1Exists ]
        try {
            for (imageTag in image1Tags) {
                if (imageTag != "") {
                    sh "docker rmi -f ${imageTag}"
                }
            }
        } catch (Exception e) {
            echo "Error: ${e}"
        }

        def latestImage2Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierTwo}-img:latest", returnStdout: true).trim()

        def versionImage2Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierTwo}-img:${newVersion}", returnStdout: true).trim()

        def defaultImage2Exists = sh(script: "docker images -q ${dockerUser}/${userName}-${appName}-${tierTwo}-img:${defaultVersion}", returnStdout: true).trim()

        def image2Tags = [ latestImage2Exists, versionImage2Exists, defaultImage2Exists ]
        try {
            for (imageTag in image2Tags) {
                if (imageTag != "") {
                    sh "docker rmi -f ${imageTag}"
                }
            }
        } catch (Exception e) {
            echo "Error: ${e}"
        }
    }
}
