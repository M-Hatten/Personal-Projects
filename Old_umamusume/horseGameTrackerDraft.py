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


print("Hello! Today's a good day to play Umamusume! :D\n")


#initializing 
umamusume = {}
with open('umamusumeData.json') as json_file:
    umamusume = json.load(json_file)

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

class State:
    def on_enter(self): pass
    def on_exit(self): pass
    def on_event(self, event):
        return self

class Idle(State):
    def on_enter(self):
        print("Entering idle state")
        print(mainMenu)

    def on_event(self, event):
        if event == "1":
            return Add()
        elif event == "2":
            return Update()
        elif event == "3":
            return Trials()
        elif event == "4":
            return Career()
        elif event == "5":
            return PowerOff()
        else:
            print("Please give a valid response")
            return self

    def on_exit(self):
        print("Exiting idle state")
#add a new umamusume
class Add(State): 
    def on_enter(self): 
        print("Entering Add state")
        name = input("Enter the trainer's name: ")
        track = input("Enter track proficiencies: ")
        distance = input("Enter distance proficiencies: ")
        style = input("Enter style profciencies: ")
        trials = input("Enter Team Trials tier score: ")
        easy = input("Enter easiness tier score: ")
        overall = input("Enter overall tier score: ")
        veteran = input("Enetr veteran score: ")
        umamusume.update({name: {"Track Proficiencies": track, "Distance Proficiencies": distance, "Style Proficiencies": style, "Team Trials Tier": trials, "Easiness Tier": easy, "Overall Tier": overall, "Veteran Score": veteran}})
    def on_exit(self): 
        print("Exiting Add state")
    def on_event(self, event):
        
        return Idle()

#update umamusume veteran score
class Update(State): 
    def on_enter(self): 
        print("Entering Update state")
    def on_exit(self): 
        print("Exiting Update state")
    def on_event(self, event):
        return self

#Team trials 
#   racer recomendations for each race type
#        sorts umamusume by their race affinity and their TT tier score
#   Current TT racers
class Trials(State): 
    def on_enter(self): 
        print("Entering Trial state")
    def on_exit(self): 
        print("Exiting Trial state")
    def on_event(self, event):
        return self

#closes the program and updates the json file
class PowerOff(State):
    def on_enter(self): 
        print("Entering Power Off state")
    def on_exit(self): 
        print("Exiting Power Off state")
    def on_event(self, event):
        return self
#Career
#   Easiest racers to send through career
#   Racers that still need a successful career run through
class Career(State): #Career menu
    def on_enter(self): 
        print("Entering Career state")
    def on_exit(self): 
        print("Exiting Career state")
    def on_event(self, event):
        return self 

class FSM:
    def __init__(self):
        self.state = Idle()
        self.state.on_enter()

    def run(self):
        event = input("What would you like to do? ")
        self.on_event(event)
    
    def on_event(self, event):
        while True:
            old_state = self.state
            new_state = old_state.on_event(event)

            if new_state is not old_state:
                old_state.on_exit()
                new_state.on_enter()
                self.state = new_state

fsm = FSM()
fsm.run()
