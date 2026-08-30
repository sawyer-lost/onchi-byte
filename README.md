        }

        .card {
            background: linear-gradient(145deg, var(--card), var(--card2));
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 20px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.22);
        }

        textarea {
            width: 100%;
            height: 300px;
            resize: vertical;
            border: 1px solid var(--border);
            border-radius: 12px;
            outline: none;
            padding: 16px;
            background: #080c14;
            color: #dce5f5;
            font-family: "Courier New", monospace;
            font-size: 14px;
            line-height: 1.7;
        }

        textarea:focus {
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(109, 124, 255, 0.12);
        }

        .buttons {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 9px;
            margin-top: 14px;
        }

        button {
            border: 1px solid var(--border);
            border-radius: 10px;
            padding: 12px 8px;
            cursor: pointer;
            color: white;
            background: #182131;
            font-weight: 800;
            transition: 0.2s;
        }

        button:hover {
            transform: translateY(-2px);
            border-color: var(--accent);
            background: #202b40;
        }

        #loadButton,
        #runButton {
            background: linear-gradient(135deg, #5968ee, #697bff);
            border: none;
        }

        #resetButton {
            background: #251d2a;
        }

        .program-list {
            margin-top: 15px;
            max-height: 180px;
            overflow-y: auto;
            border: 1px solid var(--border);
            border-radius: 10px;
            padding: 6px;
            background: #080c14;
        }

        .program-line {
            padding: 8px 10px;
            border-radius: 7px;
            color: #8491a8;
            font-family: monospace;
            font-size: 13px;
        }

        .current {
            background: rgba(109, 124, 255, 0.18);
            color: white;
            border-left: 3px solid var(--accent2);
        }

        .register-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 9px;
        }

        .register {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px;
            border: 1px solid var(--border);
            border-radius: 10px;
            background: rgba(8, 12, 20, 0.7);
        }

        .register-name {
            color: var(--muted);
            font-size: 12px;
            font-weight: bold;
        }

        .register-value {
            color: var(--accent2);
            font-family: monospace;
            font-size: 17px;
            font-weight: bold;
        }

        .status-box {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 14px;
            padding: 13px 15px;
            border: 1px solid var(--border);
            border-radius: 10px;
            background: #080c14;
        }

        .status-label {
            color: var(--muted);
            font-size: 12px;
        }

        #status {
            color: var(--green);
            font-weight: bold;
        }

        .trace-card {
            margin-top: 18px;
        }

        .trace {
            min-height: 190px;
            padding: 18px;
            border-radius: 12px;
            border: 1px solid var(--border);
            background: #05080e;
            color: #cbd5e6;
            white-space: pre-line;
            font-family: "Courier New", monospace;
            line-height: 1.7;
            overflow-x: auto;
        }

        .trace-title {
            color: var(--accent2);
            margin-bottom: 12px;
        }

        .developers {
            margin-top: 28px;
        }

        .developer-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 14px;
        }

        .developer {
            text-align: center;
            padding: 20px 12px;
            background: linear-gradient(145deg, var(--card), var(--card2));
            border: 1px solid var(--border);
            border-radius: 16px;
            transition: 0.25s;
        }

        .developer:hover {
            transform: translateY(-5px);
            border-color: var(--accent);
            box-shadow: 0 12px 30px rgba(109, 124, 255, 0.15);
        }

        .developer img {
            width: 92px;
            height: 92px;
            object-fit: cover;
            border-radius: 50%;
            border: 3px solid #27334a;
            margin-bottom: 12px;
        }

        .developer h3 {
            font-size: 15px;
        }

        .developer p {
            margin-top: 5px;
            color: var(--muted);
            font-size: 11px;
        }

        footer {
            text-align: center;
            padding: 25px;
            color: #657187;
            font-size: 12px;
            border-top: 1px solid var(--border);
        }

        @media (max-width: 850px) {
            .main-grid {
                grid-template-columns: 1fr;
            }

            .developer-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 500px) {
            .buttons {
                grid-template-columns: repeat(2, 1fr);
            }

            .developer-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .logo {
                font-size: 24px;
            }
        }
    </style>
</head>

<body>

<header>

    <div class="logo">
        <div class="logo-icon">⚡</div>
        ONCHI-BYTE
    </div>

    <p>STC89C52 Microcontroller Simulator</p>

    <div class="badge">
        ● SIMULATOR ONLINE
    </div>

</header>


<div class="container">

    <div class="main-grid">

        <!-- PROGRAM PANEL -->

        <div class="card">

            <div class="section-title">
                <span>▸</span>
                Program Editor
            </div>

            <textarea id="programInput">MOV A,#05
MOV R0,#03
ADD A,#03
INC A
ANL A,#0F
SUBB A,#02
END</textarea>

            <div class="buttons">

                <button id="loadButton">
                    LOAD
                </button>

                <button id="resetButton">
                    RESET
                </button>

                <button id="stepButton">
                    STEP
                </button>

                <button id="runButton">
                    ▶ RUN
                </button>

            </div>

            <div id="programDisplay"
                 class="program-list">
            </div>

        </div>


        <!-- CPU PANEL -->

        <div class="card">

            <div class="section-title">
                <span>▣</span>
                CPU State
            </div>

            <div class="register-grid">

                <div class="register">
                    <span class="register-name">ACCUMULATOR A</span>
                    <span id="A" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">REGISTER B</span>
                    <span id="B" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R0</span>
                    <span id="R0" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R1</span>
                    <span id="R1" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R2</span>
                    <span id="R2" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R3</span>
                    <span id="R3" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R4</span>
                    <span id="R4" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R5</span>
                    <span id="R5" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R6</span>
                    <span id="R6" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">R7</span>
                    <span id="R7" class="register-value">00</span>
                </div>

                <div class="register">
                    <span class="register-name">PROGRAM COUNTER</span>
                    <span id="PC" class="register-value">0000</span>
                </div>

                <div class="register">
                    <span class="register-name">STACK POINTER</span>
                    <span id="SP" class="register-value">07</span>
                </div>

                <div class="register">
                    <span class="register-name">CARRY FLAG</span>
                    <span id="CY" class="register-value">0</span>
                </div>

                <div class="register">
                    <span class="register-name">ZERO FLAG</span>
                    <span id="Z" class="register-value">0</span>
                </div>

            </div>

            <div class="status-box">
                <span class="status-label">
                    SYSTEM STATUS
                </span>

                <span id="status">
                    Ready
                </span>
            </div>

        </div>

    </div>


    <!-- EXECUTION TRACE -->

    <div class="card trace-card">

        <div class="section-title">
            <span>⌁</span>
            Execution Trace
        </div>

        <div class="trace-title">
            FETCH → DECODE → EXECUTE
        </div>

        <div id="trace" class="trace">
            Waiting for execution...
        </div>

    </div>


    <!-- DEVELOPERS -->

    <div class="developers">

        <div class="section-title">
            <span>◆</span>
            Developers
        </div>

        <div class="developer-grid">

            <div class="developer">

                <img
                    src="https://i.ibb.co/7tGmjPcg/25190102.jpg"
                    alt="Izhan">

                <h3>Izhan</h3>

                <p>Developer</p>

            </div>


            <div class="developer">

                <img
                    src="https://i.ibb.co/2YKbKkbc/IMG-20260826-132910-376.jpg"
                    alt="Keora">

                <h3>Keora</h3>

                <p>Developer</p>

            </div>


            <div class="developer">

                <img
                    src="https://i.ibb.co/jv8ZQ5qZ/IMG-20260826-132908-942.jpg"
                    alt="Punarvi">

                <h3>Punarvi</h3>

                <p>Developer</p>

            </div>


            <div class="developer">

                <img
                    src="https://i.ibb.co/dsGYSZRZ/IMG-20260827-WA0035.jpg"
                    alt="Hisham">

                <h3>Hisham</h3>

                <p>Developer</p>

            </div>

        </div>

    </div>

</div>


<footer>
    ONCHI-BYTE • STC89C52 Microcontroller Simulator
</footer>


<script src="./script.js"></script>


<script>

    document
        .getElementById("loadButton")
        .addEventListener("click", loadProgram);

    document
        .getElementById("resetButton")
        .addEventListener("click", resetSimulator);

    document
        .getElementById("stepButton")
        .addEventListener("click", step);

    document
        .getElementById("runButton")
        .addEventListener("click", runProgram);

</script>

</body>

</html>| 5 | Implementation of scheduling algorithms such as FCFS, Round Robin, and Priority Scheduling |
| 6 | Development of UI features such as Load, Run, Reset, and Step, followed by module integration |
| 7 | Implementation of performance metrics including waiting time, turnaround time, response time, and CPU utilization |
| 8 | Testing, documentation, bug fixing, final improvements, and project completion |


📊 **Performance Analysis**

The project is planned to analyse:

- Waiting Time
- Turnaround Time
- Response Time
- Context Switches
- CPU Utilization

---

👨‍💻 **Developers**

<table>
<tr><td align="center">
<img src="https://i.ibb.co/7tGmjPcg/25190102.jpg" width="100" height="100" style="border-radius:50%;"><br>
<b>Izhan</b>
</td><td align="center">
<img src="https://i.ibb.co/2YKbKkbc/IMG-20260826-132910-376.jpg" width="100" height="100" style="border-radius:50%;"><br>
<b>Keora</b>
</td><td align="center">
<img src="https://i.ibb.co/jv8ZQ5qZ/IMG-20260826-132908-942.jpg" width="100" height="100" style="border-radius:50%;"><br>
<b>Punarvi</b>
</td><td align="center">
<img src="https://i.ibb.co/dsGYSZRZ/IMG-20260827-WA0035.jpg" width="100" height="100" style="border-radius:50%;"><br>
<b>Hisham</b>
</td></tr>
</table>

---

 **Team Responsibilities**

- **Izhan:** OS Scheduling & Simulator Planning
- **Keora:** Data Structures & Process Management
- **Punarvi:** Memory & Stack Study
- **Hisham:** STC89C52 CPU / Architecture Study

**Secondary Responsibilities:**

- **Izhan:** UI Design
- **Keora:** Testing Plan
- **Punarvi:** Architecture Diagram
- **Hisham:** GitHub & Project Integration

---



 **Goal**

The goal of this project is to make a simple simulator which helps students understand how the STC89C52 works and how multiple programs can be managed using CPU Scheduling.

The project will be developed step by step in the coming weeks.

---

 **Project Status**

**Currently in the Initial Development / Planning Stage.**

More features and implementation details will be added as development progresses.
