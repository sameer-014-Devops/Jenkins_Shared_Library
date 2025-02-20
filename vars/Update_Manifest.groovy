// vars/Update_Manifest.groovy
#!/usr/bin/env groovy

/**
 * Updates Docker image tags in Kubernetes manifest files
 *
 * @param manifests A list of maps, each containing 'file' path and 'imagePattern' to replace
 * @param tags A map of tag names to their values
 * @param manifestDir The directory containing K8s manifest files (default: 'kubernetes')
 * @return Boolean indicating if the update was successful
 */
def call(Map config = [:]) {
    def manifests = config.manifests ?: error("manifests parameter is required")
    def tags = config.tags ?: error("tags parameter is required")
    def manifestDir = config.manifestDir ?: 'kubernetes'
    def updateSuccess = true
    
    // Create directory reference
    dir(manifestDir) {
        try {
            // Process each manifest file
            manifests.each { manifest ->
                def filePath = manifest.file
                def imagePattern = manifest.imagePattern
                def tagParam = manifest.tagParam
                
                if (!filePath || !imagePattern || !tagParam) {
                    error "Missing required parameters for manifest update: file, imagePattern, or tagParam"
                }
                
                // Get the tag value from the tags map
                def tagValue = tags[tagParam]
                if (!tagValue) {
                    error "Tag value for parameter '${tagParam}' not found in provided tags"
                }
                
                echo "Updating ${filePath} with tag ${tagValue} for pattern ${imagePattern}"
                
                // Use sed to replace the image tag in the manifest file
                sh """
                    sed -i -e 's|${imagePattern}.*|${imagePattern}:${tagValue}|g' ${filePath}
                """
                
                // Verify the change was made
                def grepResult = sh(
                    script: "grep -q '${imagePattern}:${tagValue}' ${filePath}",
                    returnStatus: true
                )
                
                if (grepResult != 0) {
                    echo "WARNING: Failed to verify update in ${filePath}"
                    updateSuccess = false
                } else {
                    echo "Successfully updated ${filePath}"
                }
            }
        } catch (Exception e) {
            echo "Error updating Kubernetes manifests: ${e.message}"
            updateSuccess = false
        }
    }
    
    return updateSuccess
}
