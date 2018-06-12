from colorama import Fore
import sys

class Writer:

    @classmethod
    def option(cls, message):
        sys.stdout.write(f"{Fore.GREEN}" + message)

    @classmethod
    def action(cls, message):
        sys.stdout.write(f"{Fore.BLUE}" + message)

    @classmethod
    def highlight(cls, message):
        sys.stdout.write(f"{Fore.LIGHTYELLOW_EX}" + str(message))


    @classmethod
    def error(cls, message, error):
        sys.stdout.write(f"{Fore.LIGHTRED_EX}" + message + "\n" + str(error))

    @classmethod
    def SQL_error(cls, message, error, query):
        sys.stdout.write(f"{Fore.LIGHTRED_EX}" + message + "\n" + str(error) + "\n" + str(query))
