package sirocco

class UserLike {
    User user

    static belongsTo = [post: Post]

    static constraints = {
    }
}
