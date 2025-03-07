pipeline {
	agent any
	
   	stages {
        stage('Verify') {
			steps {
				script {
					if("opened" == "$action" || "synchronize" == "$action" || "edited" == "$action") {
						bat 'mvn -B verify -Dgpg.skip'
					}
				}
			}
		}
        stage('Deploy') {
			steps {
				script {
					if(("closed" == "$action" && "master" == "$target" && "develop" != "$target") ||
							("closed" == "$action" && "develop" == "$target")) {
						bat 'mvn -B deploy -Dgpg.skip'
					}
				}
			}
		}
    }
    
}