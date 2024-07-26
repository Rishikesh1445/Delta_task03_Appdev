from . import db
from flask_login import UserMixin

class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(10), unique=True, nullable=False)
    password = db.Column(db.String(256), nullable=False)

class userdetails(db.Model):
    id = db.Column(db.Integer, db.ForeignKey('user.id'), primary_key=True)
    karmaPoints = db.Column(db.Float, nullable=False, default=0.0)
    username = db.Column(db.String(10), db.ForeignKey('user.username'), nullable=False)
    reputation = db.Column(db.Float, default = 0.0)
    reserved = db.Column(db.Integer, default = 0)
    completed = db.Column(db.Integer, default = 0)

    def to_dict(self):
        return {
            'id': self.id,
            'karmaPoints': self.karmaPoints,
            'username': self.username,
            'reputation': self.reputation,
            'reserved': self.reserved,
            'completed': self.completed
        }
    
    def name(self):
        return {
            'id': self.id,
            'name': self.username
        }

class tasks(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(10), nullable=False)
    description = db.Column(db.String(100))
    location = db.Column(db.String(100))
    karmaPoints = db.Column(db.Float, nullable=False, default=0.0)
    reserved = db.Column(db.Boolean, nullable = False, default = False)
    completed = db.Column(db.Boolean, nullable = False, default = False)
    shown = db.Column(db.Boolean, nullable = False, default = False)

    def to_dict(self):
        return {
            'id': self.id,
            'title': self.title,
            'description': self.description,
            'location': self.location,
            'karmaPoints': self.karmaPoints,
            'reserved': self.reserved,
            'completed': self.completed,
            'shown': self.shown
        }

class usertask(db.Model):
    taskid = db.Column(db.Integer, db.ForeignKey('tasks.id'), nullable=False, primary_key=True)
    uploaded_userid = db.Column(db.Integer, db.ForeignKey('userdetails.id'), nullable=False)
    reserved_userid =  db.Column(db.Integer, db.ForeignKey('userdetails.id'))
    completed_userid = db.Column(db.Integer, db.ForeignKey('userdetails.id'))

    def to_list(self):
        return [self.taskid, self.uploaded_userid, self.reserved_userid, self.completed_userid]
