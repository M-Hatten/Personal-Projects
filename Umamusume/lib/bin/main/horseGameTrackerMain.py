# Hello to anyone reading this!
# This application is meant to help me track my characters in a mobile game I like to play

# Capabilities
# - Lists each umamusume
# - Tracks race stats (Track type, Distance, and Style)
# - Tracks highest graded veteran score
# - Tracks what track they use in Team Trials
# - Tracks umamusume tier
# 
# Data will be kept in a dictionary for ease of access and will be stored to a json file
# This is mostly all just for practice to keep my skills sharp



import json 
from statemachine import StateMachine, State

mainMenu = """
Welcome to the main menu!

1. Add a umamusume to the roster
2. Update an umamusume's veteran score
3. View the team trials menu
4. View the career menu
5. Quit
"""

updateMenu = """
"""

trialsMenu = """
"""

careerMenu = """
"""

class HorseGame(StateMachine):
    idle = State(initial = True)
    addRacer = State(final = True)
    #updateRacer = State()
    #teamTrials = State()
    #career = State()
    #quit = State(final = True)

    cycle = (
        idle.to(addRacer)
    )

    @idle.enter
    def on_enter_idle(self):
        print ("Entered idle!")
        #print (mainMenu)
        #choice = input("What would you like to do? ")
        #if (choice == "1"):
        #    print ("Choice accepted")
        #    hg.send("addRacer")

    #def on_idle(self):
    #    print ("We're here!")

    @idle.exit
    def on_exit_idle(self):
        print ("Exiting idle!")

    @addRacer.enter
    def on_enter_addRacer(self):
        print ("Entered add racer!")

    @addRacer.exit
    def on_exit_addRacer(self):
        print ("Exiting add racer!")


hg = HorseGame()
hg.send("idle")

