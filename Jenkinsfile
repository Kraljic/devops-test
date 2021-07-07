node {
    try {
        echo 'Hello World'
        sh exit 1
        rocketSend channel: '#test-projekt', message: ':partying_face: Build of test project was success :: '
    } catch (e) {
        rocketSend channel: '#test-projekt', message: ':face_with_symbols_over_mouth: Build of test project was failed :: '
        throw e
    }
}
