def call(){

    script {

        def userName = env.dockerhubUser
        def password = env.dockerhubPasswd
        sh "echo $password | docker login -u $userName --password-stdin"

        // Check if the login is successful
        if (sh(script: 'docker info | grep -i "Username: ${userName}"', returnStatus: true) == 0) {
            echo '***********DockerHub login SUCCESSFUL***********'
        } else {
            error '**********DockerHub login FAILED**********'
        }

    }
}
