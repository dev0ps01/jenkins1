def nexus () {
    command = "curl -f -v -u admin:vamsi --upload-file frontend.zip http://172.31.9.137:8081/repository/frontend1/frontend.zip"
    def execute_state=sh{returnStdout: true script: command}
}
def make_artifact(component) {
    if(APP_TYPE == "NGINX" ) {
        command = " zip -r ${FILENAME} node_modules dist"
        def execute_com= sh(returnStdout: true, script: command)
        print execute_com
    }
    else if(APP_TYPE == "NODEJS" ) {
        command = "zip -r ${FILENAME} node_modules server.js"
        def execute_com= sh(returnStdout: true, script: command)
        print execute_com

    }
    else if(APP_TYPE == "java" ) {
        command =  "cp target/*.jar ${COMPONENT}.jar && zip -r ${FILENAME} ${COMPONENT}.jar"
        def execute_com= sh(returnStdout: true, script: command)
        print execute_com
    }
    else if (APP_TYPE == "GOLANG") {
        command = "zip -r ${FILENAME} login-ci main.go user.go tracing.go"
        def execute_com= sh(returnStdout: true, script: command)
        print execute_com
    }
}

def
