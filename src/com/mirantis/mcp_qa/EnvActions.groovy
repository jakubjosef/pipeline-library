package com.mirantis.mcp_qa

/**
 * Activate virtual environment and check k8s deployer is specified
 */

def prepareEnv() {
    sh '''
        if [ ! -r "${VENV_PATH}/bin/activate" ]; then
            echo 'Python virtual environment not found! Set correct VENV_PATH!'
            exit 1
        fi
    '''

    sh '''
        if [ ! -r "${WORKSPACE}/fuel-ccp-installer/${DEPLOY_SCRIPT_REL_PATH}" ]; then
            echo "Deploy script \"${DEPLOY_SCRIPT_REL_PATH}\" not found in" \
            "\"${WORKSPACE}/fuel-ccp-installer/\"!"
        fi
    '''

    sh '''
        . ${VENV_PATH}/bin/activate
        pip install --upgrade --upgrade-strategy=only-if-needed -r fuel_ccp_tests/requirements.txt
    '''
}

/**
 * Destroy running environment
 */

def destroyEnv() {
    if ( !(env.KEEP_BEFORE.equals('yes') || env.KEEP_BEFORE.equals('true')) ) {
        sh '''
            . ${VENV_PATH}/bin/activate
            dos.py destroy ${ENV_NAME} || true
        '''
    }
}

/**
 * Erase running environment
 */
def eraseEnv() {
        sh '''
            . ${VENV_PATH}/bin/activate
            dos.py erase ${ENV_NAME} || true
        '''
}

