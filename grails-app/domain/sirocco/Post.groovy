package sirocco

class Post {

    String content
    Date dateCreated
    Date lastUpdated
    String imageLink

    static constraints = {
        imageLink nullable: true, blank: true
        content nullable: false, blank: false
    }

    static hasMany = [likes: UserLike, comments: Comment]
    static belongsTo = [user: User]
}
