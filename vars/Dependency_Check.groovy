def call() {
    try {
        echo "########## Starting Dependency Check ##########"
        
        if (!env.NVD_API_KEY) {
            error "NVD_API_KEY is not set. Please set the NVD_API_KEY environment variable."
            echo "Change the following Command in Shared Library line number 11"
            echo"dependencyCheck additionalArguments: '--scan ./', odcInstallation: 'OWASP'"
        }
        
        dependencyCheck additionalArguments: "--scan ./ --nvdApiKey ${env.NVD_API_KEY}", odcInstallation: 'OWASP'
        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        
        echo "########## Dependency Check Completed Successfully ##########"
    } catch (Exception e) {
        echo "########## Dependency Check Failed ##########"
        echo "Error: ${e.message}"
        throw e
    }
}
