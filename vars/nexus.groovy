def nexus () {
    command = "curl -f -v -u admin:vamsi --upload-file frontend.zip http://172.31.9.137:8081/repository/frontend1/frontend.zip"
    def execute_state=sh{returnStdout: true , script: command}
}