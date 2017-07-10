package sirocco

class Comment {

    String commentText
    Date dateCreated
    Date lastUpdated

    static belongsTo = [post: Post, user: User]

    static constraints = {
        commentText blank: false, nullable: false
    }
}
