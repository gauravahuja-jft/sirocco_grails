package sirocco

class User {

    String username
    String password
    String firstName
    String lastName

    static hasMany = [posts: Post, comments: Comment, likedPosts: Post]

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        firstName nullable: false, blank: false, unique: false
        lastName nullable: false, blank: false, unique: false
    }

    static mapping = {
        password column: '`password`'
    }
}
