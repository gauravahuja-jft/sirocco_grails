package sirocco

import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->
        def me = User.findByUsername('gauravahuja') ?: new User(firstName: 'Gaurav', lastName: 'Ahuja', username: 'gauravahuja', password: 'newpass').save(failOnError: true);

        def samplePost = new Post(content: 'Voila! We have our first post. :)', user: me).save(failOnError: true);
        new Post(content: 'Awesome photography. <3', user: me, imageLink: "https://s-media-cache-ak0.pinimg.com/originals/06/e5/6a/06e56ad0cd6b4dcf7deaeae21afc2e8c.jpg").save(failOnError: true);
        new Post(content: 'Voila! We have our third post. :)', user: me).save(failOnError: true);
        new Post(content: 'Voila! We have our fourth post. :)', user: me).save(failOnError: true);
        new Post(content: 'Voila! We have our fifth post. :)', user: me).save(failOnError: true);

        //Register Comment domain for JSON rendering
        JSON.registerObjectMarshaller(Comment) {
            def output = [:]
            output['id'] = it.id
            output['commentText'] = it.commentText
            output['dateCreated'] = it.dateCreated
            output['lastUpdated'] = it.lastUpdated
            output['post'] = ["id": it.post.id]
            output['user'] = ["id": it.user.id, "username": it.user.username]
            return output;
        }

        //Register User domain for JSON rendering
        JSON.registerObjectMarshaller(User) {
            def output = [:]
            output['id'] = it.id
            output['firstName'] = it.firstName
            output['lastName'] = it.lastName
            output['username'] = it.username
            return output;
        }
    }
    def destroy = {
    }
}
