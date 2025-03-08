def call(String GitURL, String GitBranch) {
    
    try {
        echo "Cloning repository from ${GitURL} on branch ${GitBranch}"
        git url:"${GitURL}",branch:"${GitBranch}"
        echo "**********Repository cloned successfully**********"
    } catch (Exception e) {
        echo "Failed to clone repository: ${e.message}"
        throw e
    }
    
}
