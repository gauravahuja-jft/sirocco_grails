package sirocco

import grails.converters.JSON
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

class CommentController extends RestfulController {
    static responseFormats = ['json', 'xml']

    CommentController() {
        super(Comment)
    }


}
