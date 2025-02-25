def call(String dockerUser, String dockerPasswd){
    
    script{
        
        sh "echo ${dockerPasswd} | docker login -u ${dockerUser} --password-stdin"
        if (sh(script: "docker info | grep -i 'Username: ${dockerUser}'", returnStatus: true) == 0) {
            echo "********* DockerHub Login SUCCESSFUL with User ${dockerUser} *********"
        } else {
            error '********* DockerHub Login FAILED *********'
        }
        
    }
}
