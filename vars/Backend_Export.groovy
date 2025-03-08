def call(){

    script {

        def ipv4Address = env.tierTwoIP
        def filePath = './.env.docker'
        def file = new File(filePath)
    
        if (!file.exists()) {
            error "ERROR: File not found: $filePath"
        }
        
        def currentUrl = file.readLines()[3] // Read the 4th line (index 3)
        def newUrl = "FRONTEND_URL=\"http://$ipv4Address:5173\""
        
        if (!currentUrl.contains(newUrl)) {
            file.text = file.text.replaceAll(/FRONTEND_URL.*/, newUrl)
            echo "Updated $filePath with new FRONTEND_URL."
        } else {
            echo "No changes needed in $filePath."
        }

    }
}
