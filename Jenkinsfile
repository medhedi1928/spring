pipeline {
    agent {
        label "master"
    }
    tools {
        maven 'maven'
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.1.136:8081"
        NEXUS_REPOSITORY = "maven-releases"
        NEXUS_CREDENTIAL_ID = "nexus"
    }
    stages {
        stage("Clone code from VCS") {
            steps {
                script {
git branch: 'med', url: 'git@gitlab.com:Med_Hedi_Ben_khoudjha/spring-timesheet.git'  
}
            }
        }
                 stage("Compile") {
          steps  {

                 bat  'mvn compile '
          }
      }
  stage("Test") {
          steps  {

                 bat  'mvn  test'
          }
      }
         stage("build & SonarQube analysis") {
          steps  {
              withSonarQubeEnv('sonar') {
                 bat  'mvn clean install  package sonar:sonar'
              }
          }
      }
     
        stage("Maven Build") {
            steps {
                script {
                    bat "mvn package -DskipTests=true"
                }
            }
        }
        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
    post { 

 success {  
             mail bcc: '', body: "The pipeline ${currentBuild.fullDisplayName} completed successfully.", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Pipeline has succes: ${currentBuild.fullDisplayName}", to: "mohamedhedi.benkhoudja@esprit.tn";  
         }  
         failure {  
             mail bcc: '', body: "Error in ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Pipeline has failed: ${currentBuild.fullDisplayName}", to: "mohamedhedi.benkhoudja@esprit.tn";  
         } 
    }
}