def call() {
    try {
        echo "########## Starting Dependency Check ##########"
        dependencyCheck additionalArguments: "--scan ./ --nvdApiKey ${NVD_API_KEY}", odcInstallation: 'OWASP'
        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        echo "########## Dependency Check Completed Successfully ##########"
    } catch (Exception e) {
        echo "########## Dependency Check Failed ##########"
        echo "Error: ${e.message}"
        throw e
    }
}
