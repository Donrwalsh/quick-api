import pymysql
from python import identity
from python.writer_service import Writer


class Database(object):

    def __init__(self):
        try:
            self.conn = pymysql.connect(host=identity.host,
                                        user=identity.user,
                                        passwd=identity.passwd,
                                        db=identity.db,
                                        cursorclass=pymysql.cursors.DictCursor)
            self.cur = self.conn.cursor()
        except pymysql.err.DatabaseError as e:
            Writer.error("\nCritical error connecting to database", e)
            exit()
        else:
            Writer.action("Connected to database.")
        self.conn.set_charset('utf8')