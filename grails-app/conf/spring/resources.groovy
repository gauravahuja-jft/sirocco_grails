import grails.rest.render.json.*
import sirocco.Comment
import sirocco.User

// Place your Spring DSL code here
beans = {
    userRenderer(JsonRenderer, User) {
        excludes = ['password', 'posts', 'likedPosts', 'comments']
    }
    userCollectionRenderer(JsonCollectionRenderer, User) {
        excludes = ['password', 'posts', 'likedPosts', 'comments']
    }
}
