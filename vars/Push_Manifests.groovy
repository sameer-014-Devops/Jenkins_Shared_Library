// vars/Push_Manifests.groovy
#!/usr/bin/env groovy

/**
 * Commits and pushes changes to a Git repository with credential handling
 *
 * @param credentialsId Jenkins credentials ID for Git authentication
 * @param commitMessage Message to use for the commit
 * @param repository Git repository URL to push changes to
 * @param branch Branch to push changes to (default: 'main')
 * @param gitTool Git tool name to use (default: 'Default')
 * @param showDiff Whether to show git diff before committing (default: false)
 * @return Boolean indicating if the operations were successful
 */
def call(Map config = [:]) {
    def credentialsId = config.credentialsId ?: error("credentialsId parameter is required")
    def commitMessage = config.commitMessage ?: "Updated files from Jenkins pipeline"
    def repository = config.repository ?: error("repository parameter is required")
    def branch = config.branch ?: 'master'
    def gitTool = config.gitTool ?: 'Default'
    def showDiff = config.showDiff ?: false
    def success = false
    
    try {
        withCredentials([gitUsernamePassword(credentialsId: credentialsId, gitToolName: gitTool)]) {
            // Check repository status
            echo "Checking repository status:"
            sh 'git status'
            
            // Optionally show diff
            if (showDiff) {
                echo "Showing changes to be committed:"
                sh 'git diff --staged'
            }
            
            // Add changes to git
            echo "Adding changes to git:"
            sh 'git add .'
            
            // Check if there are changes to commit
            def hasChanges = sh(
                script: 'git diff --cached --quiet || echo "has_changes"',
                returnStdout: true
            ).trim()
            
            if (hasChanges == "has_changes") {
                // Commit changes
                echo "Committing changes:"
                sh "git commit -m \"${commitMessage}\""
                
                // Push changes
                echo "Pushing changes to repository:"
                sh "git push ${repository} ${branch}"
                
                // Verify push was successful
                def pushStatus = sh(
                    script: 'git push -v 2>&1 | grep -q "Everything up-to-date" || echo "pushed"',
                    returnStdout: true
                ).trim()
                
                if (pushStatus != "pushed") {
                    echo "Git push complete - changes were successfully pushed"
                } else {
                    echo "Git push complete - no changes were pushed or all changes were already up-to-date"
                }
                
                success = true
            } else {
                echo "No changes to commit. Repository is up to date."
                success = true
            }
        }
    } catch (Exception e) {
        echo "Error during Git operations: ${e.message}"
        success = false
    }
    
    return success
}
