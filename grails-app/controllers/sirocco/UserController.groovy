package sirocco


import grails.rest.*
import grails.converters.*

class UserController extends RestfulController<User> {
    static responseFormats = ['json']
    UserController() {
        super(User)
    }

    def comments(){
        def userId = params.userId;
        def comments = Comment.findAllByUser(User.findById(userId))
        render comments as JSON
    }

    def likedPosts(){
        def user = User.findById(params.userId);
        def userLikes = UserLike.findAllByUser(user).toSet();
        def likedPosts = userLikes*.post
        render likedPosts as JSON
    }

    def posts(){
        def user = User.findById(params.userId);
        def posts = Post.findAllByUser(user);
        render posts as JSON
    }
}
