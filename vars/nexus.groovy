def nexus (COMPONENT) {
    get_branch = "env | grep GIT_BRANCH | awk -F / '{print \$NF}' | xargs echo -n"
    def get_branch_exec=sh(returnStdout: true, script: get_branch)
    def FILENAME=COMPONENT+'-'+get_branch_exec+'.zip'
    command = "curl -f -v -u admin:vamsi --upload-file ${FILENAME} http://172.31.9.137:8081/repository/${COMPONENT}/${FILENAME}"
    def execute_state=sh(returnStdout: true , script: command)
}
def artifacts(APP_TYPE,COMPONENT) {
    get_branch = "env | grep GIT_BRANCH | awk -F / '{print \$NF}' | xargs echo -n"
    def get_branch_exec=sh(returnStdout: true, script: get_branch)
    println("abc${get_branch_exec}abc")
    def FILENAME=COMPONENT+'-'+get_branch_exec+'.zip'
    if(APP_TYPE == "NGINX" ) {
        command = " zip -r ${FILENAME} node_modules dist"
        def execute= sh(returnStdout: true, script: command)
        print execute
    }
    else if(APP_TYPE == "NODEJS" ) {
        command = "zip -r ${FILENAME} node_modules server.js"
        def execute= sh(returnStdout: true, script: command)
        print execute

    }
    else if(APP_TYPE == "java" ) {
        command =  "cp target/*.jar ${COMPONENT}.jar && zip -r ${FILENAME} ${COMPONENT}.jar"
        def execute= sh(returnStdout: true, script: command)
        print execute
    }
    else if (APP_TYPE == "GOLANG") {
        command = "zip -r ${FILENAME} login-ci main.go user.go tracing.go"
        def execute= sh(returnStdout: true, script: command)
        print execute
    }
}

def code(APP_TYPE,COMPONENT) {
    if(APP_TYPE == 'NODEJS') {
        command = "npm install"
        def execute= sh(returnStdout: true, script: command)
        print execute
    }
    else if(APP_TYPE == 'GOLANG') {
        command = "go get -d && go build"
        def execute = sh(returnStdout: true, script: command)
        print execute
    }
    else if(APP_TYPE == 'JAVA') {
        command = "mvn clean package"
        def execute = sh(returnStdout: true, script: command)
        print execute
    }
    else if(APP_TYPE == "NGINX") {
        command = "npm install && npm run build"
        def execute = sh(returnStdout: true, script: command)
        print execute
    }
}
