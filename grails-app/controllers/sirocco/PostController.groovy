package sirocco

import grails.rest.*
import grails.converters.*
import net.sf.ehcache.exceptionhandler.ExceptionHandlingDynamicCacheProxy
import org.springframework.http.HttpStatus

class PostController extends RestfulController {
    static allowedMethods = [ addComment: 'POST', createPost: 'POST']
    static responseFormats = ['json', 'xml']

    PostController() {
        super(Post)
    }

    @Override
    def index(){
        params.max = Math.min(params.max as Integer ?: 10, 100)
        def posts = Post.listOrderByDateCreated(max: params.max, order: "desc")
        render posts as JSON
    }

    def likedByUsers(){
        Long postId = params.postId as Long;
        def userLikes = Post.findById(postId).getLikes();
        def users = userLikes*.user
        render users as JSON
    }

    def getComments(){
        def post = Post.findById(params.postId);
        def comments = Comment.findAllByPost(post);
        render comments as JSON
    }

    def createPost(){
        def params = request.getJSON();
        def content = params.content;
        def user = User.findById(params.userId);
        def imageLink = null;
        if(params.imageLink){
            imageLink = "/opt/siroccoImages/" + params.imageLink
        }
        println "content : " + content + "user : " + params.userId + "imageLink : " + imageLink
        try{
            new Post(content: content, user: user, imageLink: imageLink).save(failOnError: true, flush: true);
            response.sendError(200)
        } catch (Exception e){
            response.sendError(400)
        }

    }

    def addComment(){
        def params = request.getJSON();
        def commentText = params.commentText;
        def user = User.findById(params.userId);
        def post = Post.findById(params.postId);
        try{
            new Comment(commentText: commentText, user: user, post: post).save(failOnError: true, flush: true);
            render HttpStatus.CREATED;
        } catch (Exception e){
            render HttpStatus.BAD_REQUEST;
        }
    }

    def likesCount() {
        Long postId = params.postId as Long;
        def likesCount = Post.findById(postId).getLikes().size()
        def result = new HashMap();
        result.put('likesCount', likesCount)
        render result as JSON
    }

    def commentsCount() {
        def post = Post.findById(params.postId)
        def commentsCount = Comment.findAllByPost(post).size();
        def result = new HashMap();
        result.put('commentsCount', commentsCount)
        render result as JSON
    }

    def likePost() {
        Long postId = params.postId as Long;
        Long userId = params.userId as Long;
        def post = Post.findById(postId);
        def user = User.findById(userId);
        try{
            def userLike = new UserLike(user: user, post: post).save(flush: true);
            response.sendError(200)
        } catch (Exception e){
            render HttpStatus.CONFLICT
        }
    }

    def uploadImage(){
        def file = request.getFile('file')
        if (file.empty) {
            flash.message = 'file cannot be empty'
            return
        }
        String randomFilename = UUID.randomUUID().toString() + ".jpg";
        file.transferTo(new File("/opt/siroccoImages/" + randomFilename))
        response.sendError(200, randomFilename)
    }

}
